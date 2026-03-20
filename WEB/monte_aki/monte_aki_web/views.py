from django.shortcuts import render
from .models import Produto

# Create your views here.
from django.shortcuts import render, redirect

#login
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib import messages

#cadastro
from django.contrib.auth.models import User

#funçao que vai realizar o login
def login_view(request):
    if request.method == 'POST':

        #obtendo o usuario e senha digitada no html
        username = request.POST.get('username')
        password = request.POST.get('password')

        #fazer a autenticação
        user = authenticate(request, username=username, password=password)

        #verificação
        if user is not None:
            login(request, user)
            return redirect('home')
        else:
            messages.error(request,'usuario ou senha inválidos!')
    
    return render(request, 'login.html')


#função que levará para a home
@login_required(login_url='login')
def home(request):
    produtos = Produto.objects.all()

    return render(request, 'home.html', {
        'produtos': produtos
    })

# função que desconetará o usuario do site
def logout_view(request):
    logout(request)
    return redirect('login') 


def cadastro(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        email = request.POST.get('email')
        password = request.POST.get('password')

        #verificar se o email ja existe
        if User.objects.filter(email=email).exists():
            messages.error(request,"Email já cadastrado!")
            return redirect('cadastro')
        
        #criar nosso objeto User e salvar no banco
        User.objects.create_user(
            username=username,
            email=email,
            password=password
        )

        messages.success(request, "Cadastro realizado com sucesso!")
        return redirect('login')

    return render(request,'cadastro.html')


#------TELA MONTAGEM-----------
def montagem(request):
    produtos = Produto.objects.all()

    return render(request, 'montagem.html', {
        'produtos': produtos
    })


def hardware(request):
    produtos = Produto.objects.all()

    return render(request, 'hardware.html', {
        'produtos': produtos
    })


def selecionar_produto(request):#Recebe dados do java Script

    if request.method == "POST":

        data = json.loads(request.body)#Converte o JSON enviado pelo JavaScript para um dicionário Python.

        nome = data["nome"]
        preco = data["preco"]

        if "pc" not in request.session:#Converte o JSON enviado pelo JavaScript para um dicionário Python.
            request.session["pc"] = []

        request.session["pc"].append({#Adicionar peça
            "nome": nome,
            "preco": preco
        })

        request.session.modified = True #Força o Django a salvar a sessão.

        return JsonResponse({"status": "ok"}) #Resposta enviada ao JavaScript:
    
#Tela do PC montado
def pc_montado(request):

    pc = request.session.get("pc", [])#Pega os dados salvos na sessão.

    total = 0

    for item in pc:
        total += float(item["preco"])#Soma o preço de todas as peças.

    return render(request, "pc_montado.html", {#Enviar para o HTML

        "pc": pc,
        "total": total

    })


#Detalhes do produto
def produto_detalhes(request, id):

    produto = Produto.objects.get(id_produto=id)

    data = {
        "descricao": produto.descricao,
        "info": produto.informacoes_tecnicas
    }

    return JsonResponse(data)


#------TELA HARDWARE-----------

def hardware(request):
    produtos = Produto.objects.all()

    return render(request, 'hardware.html', {
        'produtos': produtos
    })


#------TELA PCGAMER-----------

def pcgamer(request):
    produtos = Produto.objects.all()

    return render(request, 'pcgamer.html', {
        'produtos': produtos
    })


#------TELA PERIFERICOS-----------

def perifericos(request):
    produtos = Produto.objects.all()

    return render(request, 'perifericos.html', {
        'produtos': produtos
    })


#------TELA ESCRITORIO-----------

def escritorio(request):
    produtos = Produto.objects.all()

    return render(request, 'escritorio.html', {
        'produtos': produtos
    })
