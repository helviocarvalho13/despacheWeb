var DespacheWeb = DespacheWeb || {};

DespacheWeb.ComboEstadoNaturalidade = (function(){
	function ComboEstadoNaturalidade(){
		this.combo = $('#estadoNaturalidade');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboEstadoNaturalidade.prototype.iniciar = function(){
		this.combo.on('change', onEstadoNaturalidadeAlterado.bind(this));
	}
	
	function onEstadoNaturalidadeAlterado(){
		this.emitter.trigger('alterado', this.combo.val());
	}

	return ComboEstadoNaturalidade;
	
}());

DespacheWeb.ComboNaturalidade = (function(){
	function ComboNaturalidade(comboEstadoNaturalidade){
		this.comboEstadoNaturalidade = comboEstadoNaturalidade;
		this.combo = $('#naturalidade');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenNaturalidadeSelecionada = $('#inputHiddenNaturalidadeSelecionada');
	}
	
	ComboNaturalidade.prototype.iniciar = function(){
		reset.call(this);
		this.comboEstadoNaturalidade.on('alterado', onEstadoNaturalidadeAlterado.bind(this));
		var codigoEstadoNaturalidade = this.comboEstadoNaturalidade.combo.val();
		inicializarCidades.call(this, codigoEstadoNaturalidade);
	}
	
	function onEstadoNaturalidadeAlterado(evento, codigoEstado){
		this.inputHiddenNaturalidadeSelecionada.val('');
		inicializarCidades.call(this, codigoEstado);
	}
	
	function inicializarCidades(codigoEstado){
		if(codigoEstado){
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: {'estado': codigoEstado},
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarCidadesFinalizado.bind(this));
		}else{
			reset.call(this);
		}
	}
	
	function onBuscarCidadesFinalizado(cidades){
		
		//alert(cidades);
		var options = [];
		cidades.forEach(function(cidade){
			options.push('<option value="'+cidade.codigo+'">'+cidade.nome+'</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoCidadeSelecionada = this.inputHiddenNaturalidadeSelecionada.val();
		if(codigoCidadeSelecionada){
			this.combo.val(codigoCidadeSelecionada);
		}
	}
	
	function reset(){
		this.combo.html('<option value="">Selecione a cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao(){
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao(){
		this.imgLoading.hide();
	}

	return ComboNaturalidade;
	
}());











DespacheWeb.ComboEstado = (function(){
	function ComboEstado(){
		this.combo = $('#estado');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboEstado.prototype.iniciar = function(){
		this.combo.on('change', onEstadoAlterado.bind(this));
	}
	
	function onEstadoAlterado(){
		this.emitter.trigger('alterado', this.combo.val());
	}

	return ComboEstado;
	
}());

DespacheWeb.ComboCidade = (function(){
	function ComboCidade(comboEstado){
		this.comboEstado = comboEstado;
		this.combo = $('#cidade');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenCidadeSelecionada = $('#inputHiddenCidadeSelecionada');
	}
	
	ComboCidade.prototype.iniciar = function(){
		reset.call(this);
		this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
		var codigoEstado = this.comboEstado.combo.val();
		inicializarCidades.call(this, codigoEstado);
	}
	
	function onEstadoAlterado(evento, codigoEstado){
		this.inputHiddenCidadeSelecionada.val('');
		inicializarCidades.call(this, codigoEstado);
	}
	
	function inicializarCidades(codigoEstado){
		if(codigoEstado){
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: {'estado': codigoEstado},
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarCidadesFinalizado.bind(this));
		}else{
			reset.call(this);
		}
	}
	
	function onBuscarCidadesFinalizado(cidades){
		
		//alert(cidades);
		var options = [];
		cidades.forEach(function(cidade){
			options.push('<option value="'+cidade.codigo+'">'+cidade.nome+'</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		if(codigoCidadeSelecionada){
			this.combo.val(codigoCidadeSelecionada);
		}
	}
	
	function reset(){
		this.combo.html('<option value="">Selecione a cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao(){
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao(){
		this.imgLoading.hide();
	}

	return ComboCidade;
	
}());

$(function() {
	var comboEstado = new DespacheWeb.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new DespacheWeb.ComboCidade(comboEstado);
	comboCidade.iniciar();
	
	var comboEstadoNaturalidade = new DespacheWeb.ComboEstadoNaturalidade();
	comboEstadoNaturalidade.iniciar();
	
	var comboNaturalidade = new DespacheWeb.ComboNaturalidade(comboEstadoNaturalidade);
	comboNaturalidade.iniciar();

	
});