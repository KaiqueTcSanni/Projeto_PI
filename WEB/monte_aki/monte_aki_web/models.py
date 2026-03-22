from django.db import models
from django.contrib.auth.models import User

class Produto(models.Model):
    id_produto = models.AutoField(primary_key=True)
    nome_produto = models.CharField(max_length=255)
    tipo_produto = models.CharField(max_length=255)
    fornecedor = models.CharField(max_length=255)
    descricao = models.CharField(max_length=255)
    valor = models.DecimalField(max_digits=7, decimal_places=2)
    foto_produto = models.ImageField(upload_to='produtos/')

    class Meta:
        db_table = 'tbl_produtos'

    def __str__(self):
        return self.nome_produto


class Pedido(models.Model):
    id_pedido = models.AutoField(primary_key=True)
    usuario = models.ForeignKey(User, on_delete=models.CASCADE, verbose_name="Cliente")
    data_pedido = models.DateTimeField(auto_now_add=True, verbose_name="Data do Pedido")
    valor_total = models.DecimalField(max_digits=10, decimal_places=2, verbose_name="Total R$")
    
    STATUS_CHOICES = [
        ('Pendente', 'Pendente'),
        ('Pago', 'Pago'),
        ('Cancelado', 'Cancelado'),
    ]
    status = models.CharField(max_length=20, choices=STATUS_CHOICES, default='Pendente')

    class Meta:
        db_table = 'tbl_pedidos'
        verbose_name = "Pedido"
        verbose_name_plural = "Pedidos"

    def __str__(self):
        return f"Pedido {self.id_pedido} - {self.usuario.username}"

class ItemPedido(models.Model):
    id_item = models.AutoField(primary_key=True)
    pedido = models.ForeignKey(Pedido, related_name='itens', on_delete=models.CASCADE)
    nome_produto_selecionado = models.CharField(max_length=255)
    preco_unitario = models.DecimalField(max_digits=10, decimal_places=2)
    foto_url = models.CharField(max_length=500, null=True, blank=True)

    class Meta:
        db_table = 'tbl_itens_pedido'
        verbose_name = "Item do Pedido"
        verbose_name_plural = "Itens dos Pedidos"

    def __str__(self):
        return f"{self.nome_produto_selecionado} (Pedido {self.pedido.id_pedido})"