from django.urls import path
from . import views

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
    path('remover/<int:indice>/', views.remover_do_carrinho, name='remover_do_carrinho'),
    path('reiniciar-build/', views.reiniciar_build, name='reiniciar_build'),
    path('adicionar-ao-carrinho/', views.adicionar_ao_carrinho, name='adicionar_ao_carrinho'),
    path('salvar-pc-completo/', views.salvar_pc_completo, name='salvar_pc_completo'),
    path('finalizar/', views.finalizar_pedido, name='finalizar_pedido'),
    path('meus-pedidos/', views.meus_pedidos, name='meus_pedidos'),
    path('produto/<int:id_produto>/', views.detalhe_produto, name='detalhe_produto'),
    
]