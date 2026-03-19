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

def montagem(request):
    return render(request, 'montagem.html')

def hardware(request):
    return render(request, 'hardware.html')

