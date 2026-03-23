from django.apps import AppConfig

class MonteAkiWebConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'monte_aki_web'

    def ready(self):
        import monte_aki_web.views
