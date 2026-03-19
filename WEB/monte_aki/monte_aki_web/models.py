from django.db import models

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
