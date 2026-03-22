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

def formatar_br(valor_float):
    """Auxiliar para formatar float para String de Real (1.500,00)"""
    return f"{valor_float:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')

def limpar_preco(preco_str):
    """Auxiliar para converter 'R$ 1.500,00' ou '1500.00' em float"""
    if not preco_str: return 0.0
    limpo = str(preco_str).replace('R$', '').replace('.', '').replace(',', '.').replace(' ', '').strip()
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
        'Resfriamento'   
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
        'Fonte', 'Gabinete', 'Memória RAM', 'Armazenamento', 'Resfriamento',
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
    return render(request, 'escritorio.html', {'produtos': Produto.objects.all()})

def montagem(request):
    return render(request, 'montagem.html', {'produtos': Produto.objects.all()})

def salvar_pc_completo(request):
    if request.method == "POST":
        data = json.loads(request.body)
        itens = data.get('itens', [])
        carrinho_sessao = []
        for item in itens:
            carrinho_sessao.append({
                'nome': item.get('nome'),
                'preco': item.get('preco'),
                'foto': item.get('img'),
            })
        
        request.session['pc'] = carrinho_sessao
        request.session.modified = True
        
        return JsonResponse({"status": "sucesso", "redirect": "/carrinho/"})

def adicionar_ao_carrinho(request):
    if request.method == "POST":
        try:
            data = json.loads(request.body)
            p = Produto.objects.get(id_produto=data.get("id"))
            
            novo_item = {
                'id': p.id_produto,
                'nome': p.nome_produto,
                'preco': formatar_br(p.valor),
                'foto': p.foto_produto.url if p.foto_produto else "/static/resources/sem-foto.png", 
                'tipo': p.tipo_produto
            }
            
            carrinho = request.session.get('pc', [])
            carrinho.append(novo_item)
            request.session['pc'] = carrinho
            request.session.modified = True
            return JsonResponse({'status': 'sucesso'})
        except Exception as e:
            return JsonResponse({'status': 'erro', 'message': str(e)}, status=400)



def carrinho(request):
    pc = request.session.get('pc', [])
    total_acumulado = 0.0

    for item in pc:
        preco_bruto = item.get('preco', '0')
        
        if isinstance(preco_bruto, str):
            limpo = preco_bruto.replace('R$', '').strip()
            if ',' in limpo and '.' in limpo:
                limpo = limpo.replace('.', '').replace(',', '.')
            elif ',' in limpo:
                limpo = limpo.replace(',', '.')
            
            try:
                total_acumulado += float(limpo)
            except ValueError:
                pass
        else:
            total_acumulado += float(preco_bruto)

    return render(request, 'carrinho.html', {
        'pc': pc,
        'total': "{:,.2f}".format(total_acumulado).replace(",", "v").replace(".", ",").replace("v", ".")
    })

def formatar_br(valor):
    return "{:,.2f}".format(valor).replace(",", "v").replace(".", ",").replace("v", ".")

@login_required(login_url='login')
def remover_do_carrinho(request, indice):
    pc = request.session.get("pc", [])
    if 0 <= int(indice) < len(pc):
        pc.pop(int(indice))
        request.session["pc"] = pc
        request.session.modified = True
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
    pc = request.session.get('pc', [])
    
    if not pc:
        messages.warning(request, "Seu carrinho está vazio.")
        return redirect('carrinho')
    total_acumulado = 0.0
    for item in pc:
        total_acumulado += limpar_preco(item.get('preco', '0'))

    novo_pedido = Pedido.objects.create(
        usuario=request.user,
        valor_total=total_acumulado,
        status='Pendente'
    )

    for item in pc:
        preco_item = limpar_preco(item.get('preco', '0'))
        ItemPedido.objects.create(
            pedido=novo_pedido,
            nome_produto_selecionado=item.get('nome'), 
            preco_unitario=preco_item,
            foto_url=item.get('foto')
        )

    request.session['pc'] = []
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