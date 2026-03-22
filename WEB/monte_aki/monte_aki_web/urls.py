from django.urls import path
from . import views  # Importa o arquivo views como um módulo

urlpatterns = [
    path('', views.home, name='home'),
    path('login/', views.login_view, name='login'),
    path('logout/', views.logout_view, name='logout'),
    path('cadastro/', views.cadastro, name='cadastro'),
    path('montagem/', views.montagem, name='montagem'),
    path('hardware/', views.hardware, name='hardware'),
    path('pcgamer/', views.pcgamer, name='pcgamer'),
    path('perifericos/', views.perifericos, name='perifericos'),
    path('escritorio/', views.escritorio, name='escritorio'),
    path('carrinho/', views.carrinho, name='carrinho'),
    path('pc-montado/', views.pc_montado, name='pc_montado'),
    path('selecionar-produto/', views.selecionar_produto, name='selecionar_produto'),
    path('remover/<int:indice>/', views.remover_do_carrinho, name='remover_do_carrinho'),
    path('reiniciar-build/', views.reiniciar_build, name='reiniciar_build'),
]