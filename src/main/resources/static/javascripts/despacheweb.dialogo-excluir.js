DespacheWeb = DespacheWeb || {};

DespacheWeb.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn')
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluído com sucesso!', 'success');
		}
	}
	
	function onExcluirClicado(event) {
		event.preventDefault();
		var botaoClicado = $(event.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		if(confirm("Tem certeza em excluir " +objeto+" ? Você não poderá recuperar depois!") == false){
			return;
		}else{
			onExcluirConfirmado(url);
		}
		
		//swal({
			//title: 'Tem certeza?',
			//text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			//showCancelButton: true,
			//confirmButtonColor: '#DD6B55',
			//confirmButtonText: 'Sim, exclua agora!',
			//closeOnConfirm: false
		//}, onExcluirConfirmado.bind(this, url));
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onErroExcluir(e) {
		//swal('Oops!', "Não foi possível excluir a informação. Verifique se a mesma possui algum vínculo com outro cadastro.", 'error');
		alert("Não foi possível excluir a informação. Verifique se a mesma possui algum vínculo com outro cadastro.");
	}
	
	return DialogoExcluir;
	
}());

$(function() {
	var dialogo = new DespacheWeb.DialogoExcluir();
	dialogo.iniciar();
});

