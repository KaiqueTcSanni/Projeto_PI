from django.contrib import admin
from django.utils.html import format_html
from .models import Pedido, ItemPedido

class ItemPedidoInline(admin.TabularInline):
    model = ItemPedido
    extra = 0
    readonly_fields = ('nome_produto_selecionado', 'preco_unitario', 'exibir_foto')
    fields = ('exibir_foto', 'nome_produto_selecionado', 'preco_unitario')

    def exibir_foto(self, obj):
        if obj.foto_url:
            return format_html('<img src="{}" style="width: 50px; height: auto; border-radius: 5px;" />', obj.foto_url)
        return "Sem foto"
    
    exibir_foto.short_description = "Miniatura"

@admin.register(Pedido)
class PedidoAdmin(admin.ModelAdmin):
    list_display = ('id_pedido', 'usuario', 'data_pedido', 'valor_total', 'status_colorido')
    
    list_filter = ('status', 'data_pedido')
    
    search_fields = ('usuario__username', 'id_pedido')
    
    inlines = [ItemPedidoInline]
    
    def status_colorido(self, obj):
        cores = {
            'Pendente': 'orange',
            'Pago': 'green',
            'Cancelado': 'red'
        }
        return format_html(
            '<strong style="color: {};">{}</strong>',
            cores.get(obj.status, 'black'),
            obj.status
        )
    status_colorido.short_description = "Status"