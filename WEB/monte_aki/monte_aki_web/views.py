import json  # Adicionado para processar os dados do JS
from django.shortcuts import render, redirect
from django.http import JsonResponse
from .models import Produto
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from django.contrib.auth.models import User

# --- AUTENTICAÇÃO ---

def login_view(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')
        user = authenticate(request, username=username, password=password)

        if user is not None:
            login(request, user)
            proxima_pagina = request.GET.get('next', 'home') 
            return redirect(proxima_pagina)
        else:
            messages.error(request,'Usuário ou senha inválidos!')
    
    return render(request, 'login.html')

def logout_view(request):
    logout(request)
    return redirect('login') 

def cadastro(request):
    proxima_pagina = request.GET.get('next', 'login') 
    if request.method == 'POST':
        username = request.POST.get('username')
        email = request.POST.get('email')
        password = request.POST.get('password')

        if User.objects.filter(email=email).exists():
            messages.error(request,"Email já cadastrado!")
            return redirect('cadastro')
        
        User.objects.create_user(username=username, email=email, password=password)
        messages.success(request, "Cadastro realizado com sucesso!")
        return redirect(f'/login/?next={proxima_pagina}')

    return render(request, 'cadastro.html')

# --- NAVEGAÇÃO ---

def home(request):
    produtos = Produto.objects.all()
    return render(request, 'home.html', {'produtos': produtos})

def hardware(request):
    produtos = Produto.objects.all()
    return render(request, 'hardware.html', {'produtos': produtos})

def pcgamer(request):
    produtos = Produto.objects.all()
    return render(request, 'pcgamer.html', {'produtos': produtos})

def perifericos(request):
    produtos = Produto.objects.all()
    return render(request, 'perifericos.html', {'produtos': produtos})

def escritorio(request):
    produtos = Produto.objects.all()
    return render(request, 'escritorio.html', {'produtos': produtos})

# --- LÓGICA DE MONTAGEM E CARRINHO ---

def montagem(request):
    produtos = Produto.objects.all()
    return render(request, 'montagem.html', {'produtos': produtos})

def selecionar_produto(request):
    if request.method == "POST":
        data = json.loads(request.body)
        nome = data["nome"]
        # Mantemos o preço como string vindo do JS (ex: "1.500,00")
        preco = data["preco"]
        foto = data.get("foto", "") # Pega a foto se enviada

        if "pc" not in request.session:
            request.session["pc"] = []

        request.session["pc"].append({
            "nome": nome,
            "preco": preco,
            "foto": foto
        })

        request.session.modified = True 
        return JsonResponse({"status": "ok"})

def produto_detalhes(request, id):
    produto = Produto.objects.get(id_produto=id)
    data = {
        "descricao": produto.descricao,
        "info": produto.informacoes_tecnicas
    }
    return JsonResponse(data)

def obter_placas_compativeis(request):
    processador_id = request.GET.get('id')
    try:
        processador = Produto.objects.get(id_produto=processador_id)
        placas = Produto.objects.filter(
            tipo_produto="Placa Mae", 
            fornecedor__iexact=processador.fornecedor
        )
        dados = [{
            'id': p.id_produto,
            'nome': p.nome_produto,
            'preco': str(p.valor),
            'foto': p.foto_produto.url,
            'desc': p.descricao
        } for p in placas]
        return JsonResponse({'sucesso': True, 'produtos': dados})
    except Exception as e:
        return JsonResponse({'sucesso': False, 'erro': str(e)})

def validar_montagem(pc_lista):
    # Se a lista estiver vazia, nem valida, deixa passar para mostrar "Carrinho Vazio"
    if not pc_lista:
        return True, None

    itens_obrigatorios = ['Processador', 'Placa Mae', 'Memoria RAM', 'Fonte', 'Gabinete']
    
    # Criamos uma lista com todos os nomes dos itens que estão no carrinho (em minúsculo)
    nomes_no_carrinho = " ".join([item['nome'].lower() for item in pc_lista])
    
    for obrigatorio in itens_obrigatorios:
        # Verifica se o termo obrigatório existe em algum lugar dos nomes dos produtos
        # Ex: "processador" está dentro de "Processador Intel Core i7"
        if obrigatorio.lower().replace(" ", "") not in nomes_no_carrinho.replace(" ", ""):
            return False, obrigatorio
            
    return True, None

def carrinho(request):
    pc = request.session.get("pc", [])

    # REMOVEMOS OS REDIRECTS DAQUI! 
    # Agora o usuário pode entrar no carrinho mesmo que esteja vazio ou incompleto.

    total = 0
    for item in pc:
        preco_str = item['preco'].replace('R$', '').strip()
        
        if ',' in preco_str and '.' in preco_str:
            preco_str = preco_str.replace('.', '').replace(',', '.')
        elif ',' in preco_str:
            preco_str = preco_str.replace(',', '.')
            
        try:
            total += float(preco_str)
        except ValueError:
            continue
    
    total_formatado = f"{total:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')
    
    return render(request, 'carrinho.html', {
        'pc': pc,
        'total': total_formatado
    })
    pc = request.session.get("pc", [])

    # 1. Limpa mensagens antigas para não acumular no Login
    storage = messages.get_messages(request)
    for _ in storage:
        pass # Isso "consome" as mensagens existentes e as remove
    
    # 2. Validação
    completo, faltando = validar_montagem(pc)
    if pc and not completo:
        messages.error(request, f"Seu PC ainda não está pronto! Adicione um(a) {faltando} para prosseguir.")
        return redirect('montagem')
    # Validação: Se tentar ir pro carrinho sem o básico, volta pra montagem
    completo, faltando = validar_montagem(pc)
    if not completo:
        messages.warning(request, f"Seu PC ainda não está pronto! Adicione um(a) {faltando} para prosseguir.")
        return redirect('montagem')
    total = 0
    
    for item in pc:
        # Pega apenas os números e a vírgula/ponto final
        preco_str = item['preco'].replace('R$', '').strip()
        
        # Se o preço vier como "3.500,00", removemos o ponto do milhar e trocamos a vírgula por ponto
        if ',' in preco_str and '.' in preco_str:
            preco_str = preco_str.replace('.', '').replace(',', '.')
        elif ',' in preco_str:
            preco_str = preco_str.replace(',', '.')
            
        try:
            total += float(preco_str)
        except ValueError:
            continue
    
    # Formatação correta para Real Brasileiro
    total_formatado = f"{total:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')
    
    return render(request, 'carrinho.html', {
        'pc': pc,
        'total': total_formatado
    })

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
    total = 0
    for item in pc:
        # Se já for float vindo do banco, use direto. Se for string:
        p_str = str(item['preco']).replace('R$', '').strip()
        
        # Lógica robusta para tratar 1.500,00 ou 1500.00
        if ',' in p_str:
            p_str = p_str.replace('.', '').replace(',', '.')
            
        try:
            total += float(p_str)
        except:
            continue
            
    total_formatado = f"{total:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')
    return render(request, "pc_montado.html", {"pc": pc, "total": total_formatado})