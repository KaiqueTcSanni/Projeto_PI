from django.urls import path
from .views import *

urlpatterns = [
    path('', home, name='home'),
    path('login', login_view, name='login'),
    path('loguot/',logout_view, name='logout'),
    path('cadastro/',cadastro, name='cadastro'),
    path('montagem/',montagem, name='montagem'),
    path('hardware/',hardware, name='hardware'),
    path('pcgamer/',pcgamer, name='pcgamer'),
    path('perifericos/',perifericos, name='perifericos'),
    path('escritorio/',escritorio, name='escritorio')

 
]
