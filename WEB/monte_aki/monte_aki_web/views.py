import json
from django.shortcuts import render, redirect
from django.http import JsonResponse
from .models import Produto
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
from decimal import Decimal, InvalidOperation
from decimal import Decimal
from .models import Pedido, ItemPedido
from .models import Pedido
from django.shortcuts import render
from django.shortcuts import redirect, render
from .models import Pedido, ItemPedido, Produto
from django.contrib import messages
from django.shortcuts import render, get_object_or_404
from django.contrib.auth.signals import user_logged_in, user_logged_out
from django.dispatch import receiver
from .models import CarrinhoSalvo
from django.urls import reverse

def limpar_preco(preco_str):
    """Converte 'R$ 1.500,00' ou '650.00' corretamente para float"""
    if not preco_str:
        return 0.0
    
    # Se já for um número (int ou float), apenas retorna
    if isinstance(preco_str, (int, float)):
        return float(preco_str)

    # 1. Remove R$, espaços e pontos de milhar
    limpo = str(preco_str).replace('R$', '').replace(' ', '').replace('.', '')
    
    # 2. Troca a vírgula decimal por ponto (ex: "650,00" -> "650.00")
    limpo = limpo.replace(',', '.')
    
    try:
        return float(limpo)
    except ValueError:
        return 0.0

def validar_montagem(pc_lista):
    """Verifica se os tipos obrigatórios estão presentes comparando com o banco de dados"""
    
    categorias_obrigatorias = [
        'Processador', 
        'Placa Mae', 
        'Memória RAM', 
        'Fonte', 
        'Gabinete',
        'Armazenamento', 
        'Resfriamento',
        'Notebook',
        'Computador',
        ''
    ]
    
    ids_selecionados = [item.get('id') for item in pc_lista if item.get('id')]
    tipos_no_carrinho = list(Produto.objects.filter(
        id_produto__in=ids_selecionados
    ).values_list('tipo_produto', flat=True))
    tipos_no_carrinho = [t.strip() for t in tipos_no_carrinho]
    for obrigatorio in categorias_obrigatorias:
        if obrigatorio not in tipos_no_carrinho:
            return False, obrigatorio
            
    return True, None

def login_view(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')
        user = authenticate(request, username=username, password=password)
        if user is not None:
            login(request, user)
            return redirect(request.GET.get('next', 'home'))
        else:
            messages.error(request, 'Usuário ou senha inválidos!')
    return render(request, 'login.html')

def logout_view(request):
    logout(request)
    return redirect('login') 

def cadastro(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        email = request.POST.get('email')
        password = request.POST.get('password')
        if User.objects.filter(email=email).exists():
            messages.error(request, "Email já cadastrado!")
            return redirect('cadastro')
        User.objects.create_user(username=username, email=email, password=password)
        messages.success(request, "Cadastro realizado com sucesso!")
        return redirect('login')
    return render(request, 'cadastro.html')


def home(request):
    categorias_oficiais = [
        'GPU','Fonte', 'Gabinete', 'Memória RAM', 'Armazenamento', 'Resfriamento',
        'Impressora', 'Audio', 'Processador', 'Notebook', 'Placa Mae',
        'Computador', 'Teclado', 'Mouse', 'Energia', 'Controle', 'Monitor', 
        'Fan', 'Cabo'  # Adicionei 'Cabo' aqui
    ]
    todos_produtos = Produto.objects.all()
    categorias = {}
    
    for cat in categorias_oficiais:
        produtos_filtrados = todos_produtos.filter(tipo_produto__iexact=cat)
        if produtos_filtrados.exists():
            categorias[cat] = produtos_filtrados
            
    return render(request, 'home.html', {'categorias': categorias})


def hardware(request):
    return render(request, 'hardware.html', {'produtos': Produto.objects.all()})

def pcgamer(request):
    return render(request, 'pcgamer.html', {'produtos': Produto.objects.all()})

def perifericos(request):
    return render(request, 'perifericos.html', {'produtos': Produto.objects.all()})

def escritorio(request):
    # Busca todos os produtos para que o HTML possa filtrar
    produtos = Produto.objects.all() 
    return render(request, 'escritorio.html', {'produtos': produtos})

def montagem(request):
    produtos = Produto.objects.all()
    for produto in produtos:
        # 1. Pegamos o valor e garantimos que seja uma string
        # Usamos replace para garantir que se vier uma vírgula perdida, ela vire ponto
        valor_cru = str(produto.valor).replace(',', '.').strip()
        
        try:
            # 2. Convertemos diretamente para float
            # Como seus valores são 1689.90 ou 12000.00, o float() funciona nativo
            valor_numerico = float(valor_cru)
            produto.valor_numerico = valor_numerico
        except (ValueError, TypeError):
            # Caso o campo esteja vazio ou inválido
            produto.valor_numerico = 0.0
            
    return render(request, 'montagem.html', {'produtos': produtos})

def salvar_pc_completo(request):
    if request.method == "POST":
        try:
            data = json.loads(request.body)
            itens_montados = data.get("itens", [])
            
            # Inicializa ou recupera o carrinho como DICIONÁRIO
            carrinho = request.session.get('carrinho', {})

            for item in itens_montados:
                id_str = str(item.get('id'))
                
                # Tratamento preventivo: remove espaços e garante formato float
                preco_final = str(item.get('preco')).replace(',', '.')
                
                carrinho[id_str] = {
                    'id': item.get('id'),
                    'nome': item.get('nome'),
                    'preco': float(preco_final), # Salva como número real
                    'foto': item.get('img'),
                    'quantidade': 1
                }

            request.session['carrinho'] = carrinho
            request.session.modified = True
            
            return JsonResponse({'status': 'sucesso', 'redirect': '/carrinho/'})
        except Exception as e:
            return JsonResponse({'status': 'erro', 'message': str(e)}, status=400)

def adicionar_ao_carrinho(request):
    if request.method == "POST":
        try:
            data = json.loads(request.body)
            p = Produto.objects.get(id_produto=data.get("id"))
            
            # Usaremos a chave 'carrinho' como um DIZIONÁRIO para facilitar a busca pelo ID
            carrinho = request.session.get('carrinho', {})
            id_str = str(p.id_produto)

            if id_str in carrinho:
                carrinho[id_str]['quantidade'] += 1
            else:
                carrinho[id_str] = {
                    'id': p.id_produto,
                    'nome': p.nome_produto,
                    'preco': float(p.valor), # Guardamos como número para facilitar cálculos
                    'foto': p.foto_produto.url if p.foto_produto else "/static/resources/sem-foto.png", 
                    'quantidade': 1
                }
            
            request.session['carrinho'] = carrinho
            request.session.modified = True
            return JsonResponse({'status': 'sucesso'})
        except Exception as e:
            return JsonResponse({'status': 'erro', 'message': str(e)}, status=400)



def carrinho(request):
    # 1. Recupera o carrinho como DICIONÁRIO (como salvo nas outras views)
    carrinho_dict = request.session.get('carrinho', {})
    lista_produtos = []
    total_geral = 0.0

    # 2. Transforma o dicionário em lista para o template e calcula totais
    for item_id, dados in carrinho_dict.items():
        try:
            # Limpeza do preço (vinda do banco ou da sessão)
            preco_un = limpar_preco(dados.get('preco', 0))
            qtd = int(dados.get('quantidade', 1))
            
            subtotal = preco_un * qtd
            total_geral += subtotal
            
            # Monta o objeto para o template
            lista_produtos.append({
                'id': item_id,
                'nome': dados.get('nome'),
                'foto': dados.get('foto'),
                'quantidade': qtd,
                'preco_unitario': preco_un,
                'preco_total': subtotal,
            })
        except (ValueError, TypeError):
            continue

    return render(request, 'carrinho.html', {
        'pc': lista_produtos, # O template espera 'pc'
        'total': total_geral
    })

def formatar_br(valor):
    return "{:,.2f}".format(valor).replace(",", "v").replace(".", ",").replace("v", ".")

def remover_do_carrinho(request, produto_id):
    carrinho = request.session.get('carrinho', {})
    id_str = str(produto_id)

    if id_str in carrinho:
        carrinho.pop(id_str)
        request.session['carrinho'] = carrinho
        request.session.modified = True
        messages.success(request, "Item removido!")
    
    return redirect('carrinho')

def reiniciar_build(request):
    if "pc" in request.session:
        del request.session["pc"]
    return redirect('montagem')

@login_required(login_url='login')
def pc_montado(request):
    pc = request.session.get("pc", [])
    total = sum(limpar_preco(item.get('preco', '0')) for item in pc)
    return render(request, "pc_montado.html", {"pc": pc, "total": formatar_br(total)})


def obter_placas_compativeis(request):
    processador_id = request.GET.get('id')
    try:
        proc = Produto.objects.get(id_produto=processador_id)
        placas = Produto.objects.filter(tipo_produto="Placa Mae", fornecedor__iexact=proc.fornecedor)
        dados = [{
            'id': p.id_produto, 'nome': p.nome_produto, 'preco': str(p.valor),
            'foto': p.foto_produto.url if p.foto_produto else "", 'desc': p.descricao
        } for p in placas]
        return JsonResponse({'sucesso': True, 'produtos': dados})
    except Exception as e:
        return JsonResponse({'sucesso': False, 'erro': str(e)})

def produto_detalhes(request, id):
    try:
        p = Produto.objects.get(id_produto=id)
        return JsonResponse({
            "descricao": p.descricao, 
            "info": getattr(p, 'informacoes_tecnicas', 'Informação não disponível')
        })
    except:
        return JsonResponse({"error": "Produto não encontrado"}, status=404)

@login_required(login_url='login')
def finalizar_pedido(request):
    # AJUSTE: O seu sistema usa a chave 'carrinho', não 'pc'
    carrinho_sessao = request.session.get('carrinho', {})
    
    if not carrinho_sessao:
        messages.warning(request, "Seu carrinho está vazio.")
        return redirect('carrinho')

    total_acumulado = 0.0
    itens_para_salvar = []

    for item_id, dados in carrinho_sessao.items():
        preco_un = limpar_preco(dados.get('preco', 0))
        qtd = int(dados.get('quantidade', 1))
        total_acumulado += (preco_un * qtd)
        
        itens_para_salvar.append({
            'nome': dados.get('nome'),
            'preco': preco_un,
            'foto': dados.get('foto'),
            'quantidade': qtd
        })

    # Criar o Pedido
    novo_pedido = Pedido.objects.create(
        usuario=request.user,
        valor_total=total_acumulado,
        status='Pendente'
    )

    # Criar os Itens do Pedido
    for item in itens_para_salvar:
        ItemPedido.objects.create(
            pedido=novo_pedido,
            nome_produto_selecionado=item['nome'], 
            preco_unitario=item['preco'],
            foto_url=item['foto']
        )

    # Limpar carrinho após sucesso
    request.session['carrinho'] = {}
    request.session.modified = True
    
    return render(request, 'pedido_sucesso.html', {'pedido': novo_pedido})

@login_required(login_url='login')
def meus_pedidos(request):
    pedidos = Pedido.objects.filter(usuario=request.user).order_by('-data_pedido').prefetch_related('itens')
    return render(request, 'meus_pedidos.html', {'pedidos': pedidos})

def finalizar_compra(request):
    if not request.user.is_authenticated:
        return redirect('login')

    carrinho = request.session.get('carrinho', {})
    pc_montado = request.session.get('pc_montado', {})

    if not carrinho and not pc_montado:
        messages.error(request, "Seu carrinho está vazio!")
        return redirect('home')

    total_geral = 0
    itens_para_salvar = []

    for produto_id, dados in carrinho.items():
        produto = Produto.objects.get(id_produto=produto_id)
        valor_item = float(produto.valor) * int(dados['quantidade'])
        total_geral += valor_item
        itens_para_salvar.append({
            'nome': produto.nome_produto,
            'preco': produto.valor,
            'foto': produto.foto_produto.url if produto.foto_produto else ''
        })

    for categoria, produto_id in pc_montado.items():
        if produto_id:
            produto = Produto.objects.get(id_produto=produto_id)
            total_geral += float(produto.valor)
            itens_para_salvar.append({
                'nome': f"Peça PC: {produto.nome_produto}",
                'preco': produto.valor,
                'foto': produto.foto_produto.url if produto.foto_produto else ''
            })

    novo_pedido = Pedido.objects.create(
        usuario=request.user,
        valor_total=total_geral,
        status='Pendente'
    )

    for item in itens_para_salvar:
        ItemPedido.objects.create(
            pedido=novo_pedido,
            nome_produto_selecionado=item['nome'],
            preco_unitario=item['preco'],
            foto_url=item['foto']
        )

    request.session['carrinho'] = {}
    request.session['pc_montado'] = {}
    
    messages.success(request, f"Pedido #{novo_pedido.id_pedido} realizado com sucesso!")
    return redirect('meus_pedidos')

def detalhe_produto(request, id_produto):
    produto = get_object_or_404(Produto, id_produto=id_produto)
    return render(request, 'detalhe_produto.html', {'produto': produto})

@receiver(user_logged_out)
def salvar_carrinho_no_logout(sender, request, user, **kwargs):
    carrinho = request.session.get('carrinho', {})
    if carrinho:
        obj, created = CarrinhoSalvo.objects.update_or_create(
            usuario=user,
            defaults={'dados_json': carrinho}
        )

@receiver(user_logged_in)
def recuperar_carrinho_no_login(sender, request, user, **kwargs):
    try:
        carrinho_salvo = CarrinhoSalvo.objects.get(usuario=user)
        request.session['carrinho'] = carrinho_salvo.dados_json
    except CarrinhoSalvo.DoesNotExist:
        pass

def atualizar_carrinho(request):
    if request.method == "POST":
        try:
            data = json.loads(request.body)
            produto_id = str(data.get('produto_id'))
            acao = data.get('acao')
            
            carrinho = request.session.get('carrinho', {})

            if produto_id in carrinho:
                if acao == 'aumentar':
                    carrinho[produto_id]['quantidade'] += 1
                elif acao == 'diminuir':
                    if carrinho[produto_id]['quantidade'] > 1:
                        carrinho[produto_id]['quantidade'] -= 1
                request.session['carrinho'] = carrinho
                request.session.modified = True
                return JsonResponse({'status': 'sucesso'})
        except Exception as e:
            return JsonResponse({'status': 'erro', 'message': str(e)}, status=400)
            
    return JsonResponse({'status': 'erro'}, status=400)

def pc_gamer(request):
    # O filtro deve ser idêntico ao que está na sua tabela (coluna tipo_produto)
    produtos = Produto.objects.filter(tipo_produto='Computador')
    
    return render(request, 'pcgamer.html', {
        'produtos': produtos, 
        'titulo_pagina': 'PC Gamer'
    })