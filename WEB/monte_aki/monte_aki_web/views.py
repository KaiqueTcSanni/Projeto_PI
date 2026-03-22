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
# --- AUXILIARES ---

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
    
    # 1. Esta lista DEVE ser idêntica aos valores da coluna 'tipo_produto' do seu banco
    categorias_obrigatorias = [
        'Processador', 
        'Placa Mae', 
        'Memória RAM', 
        'Fonte', 
        'Gabinete',
        'Armazenamento', 
        'Resfriamento'   
    ]
    
    # 2. Pegamos os IDs que o JavaScript enviou
    ids_selecionados = [item.get('id') for item in pc_lista if item.get('id')]
    
    # 3. Buscamos os tipos reais desses produtos no banco de dados
    # Isso garante que a validação pegue pelo 'tipo_produto' e não pelo nome comercial
    tipos_no_carrinho = list(Produto.objects.filter(
        id_produto__in=ids_selecionados
    ).values_list('tipo_produto', flat=True))
    
    # Limpeza básica de espaços em branco
    tipos_no_carrinho = [t.strip() for t in tipos_no_carrinho]

    # 4. Verificação de cada item obrigatório
    for obrigatorio in categorias_obrigatorias:
        if obrigatorio not in tipos_no_carrinho:
            return False, obrigatorio
            
    return True, None

# --- AUTENTICAÇÃO ---

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

# --- NAVEGAÇÃO ---

def home(request):
    return render(request, 'home.html', {'produtos': Produto.objects.all()})

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

# --- LÓGICA DE MONTAGEM E CARRINHO ---

def salvar_pc_completo(request):
    if request.method == "POST":
        data = json.loads(request.body)
        itens = data.get('itens', [])
        
        # Criamos a lista que vai para a sessão
        carrinho_sessao = []
        for item in itens:
            carrinho_sessao.append({
                'nome': item.get('nome'),
                'preco': item.get('preco'),
                'foto': item.get('img'), # Salvamos a URL da imagem que veio do JS
            })
        
        # Salva na sessão 'pc'
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
                'foto': p.foto_produto.url if p.foto_produto else "/static/resources/sem-foto.png", # Caminho da URL
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
            # 1. Remove o "R$" e espaços
            limpo = preco_bruto.replace('R$', '').strip()
            
            # 2. Se houver vírgula e ponto (ex: 1.500,00), remove o ponto e troca vírgula por ponto
            if ',' in limpo and '.' in limpo:
                limpo = limpo.replace('.', '').replace(',', '.')
            # 3. Se houver apenas vírgula (ex: 1500,00), troca por ponto
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
    # Formata como 1.500,00 (padrão brasileiro)
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

# --- APIS DE APOIO ---

def obter_placas_compativeis(request):
    processador_id = request.GET.get('id')
    try:
        proc = Produto.objects.get(id_produto=processador_id)
        # Filtra pelo fornecedor do processador para garantir compatibilidade básica
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

def finalizar_pedido(request):
    # Por enquanto, apenas redireciona para a página de sucesso ou pc_montado
    # Você pode implementar a lógica de salvar no banco de dados aqui depois
    return render(request, 'sucesso.html') # Ou a tela que você deseja exibir