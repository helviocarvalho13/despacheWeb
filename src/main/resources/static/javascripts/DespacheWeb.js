var DespacheWeb = DespacheWeb || {};

DespacheWeb.MaskCEP = (function(){
	function MaskCEP(){
		this.inputMaskCEP = $('.js-cep');
	}
	
	MaskCEP.prototype.enable = function(){
		this.inputMaskCEP.mask('00000-000');
	}

	return MaskCEP;
	
}());

DespacheWeb.MaskCpfCnpj = (function(){
	function MaskCpfCnpj(){
		this.radioTipoPessoa = $('.js-radio-tipoPessoa');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.inputCpfCnpj = $('#cpfCnpj');
	}
	
	MaskCpfCnpj.prototype.enable = function(){
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		var tipoPessoaSelecionada = this.radioTipoPessoa.filter(':checked')[0];
		if(tipoPessoaSelecionada){
			aplicarMascara.call(this, $(tipoPessoaSelecionada));
		}
	}
	
	function onTipoPessoaAlterado(evento){
		var tipoPessoaSelecionada = $(evento.currentTarget);
		aplicarMascara.call(this, tipoPessoaSelecionada);
		this.inputCpfCnpj.val('');
	}
	
	function aplicarMascara(tipoPessoaSelecionada){
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MaskCpfCnpj;
	
}());

DespacheWeb.MaskMoney = (function(){
	function MaskMoney(){
		this.decimal = $('.js-decimal');
		this.plain   = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function(){
		
		this.decimal.maskMoney({
	    	decimal: ",",
	    	thousands: "."
	    });
		
		this.plain.maskMoney({
    		precision: 0,
    		thousands: "."
		});
	}
	
	return MaskMoney;
	
}());

DespacheWeb.MaskData = (function(){
	function MaskData(){
		this.data = $('.js-data');
	}
	
	MaskData.prototype.enable = function(){
		this.data.mask('00/00/0000');
		
		this.data.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: 'true'
	    });
	}
	
	return MaskData;
	
}());

DespacheWeb.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

DespacheWeb.MaskPhoneNumber = (function(){
	
	function MaskPhoneNumber(){
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function(){
		
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		}
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputPhoneNumber.mask(maskBehavior, options)
		
	};
	
	return MaskPhoneNumber;
}());

$(function() {
	var maskMoney = new DespacheWeb.MaskMoney();
	maskMoney.enable();
	
	var maskData = new DespacheWeb.MaskData();
	maskData.enable();
	
	var maskPhoneNumber = new DespacheWeb.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCpfCnpj = new DespacheWeb.MaskCpfCnpj();
	maskCpfCnpj.enable();
	
	var maskCEP = new DespacheWeb.MaskCEP();
	maskCEP.enable();
	
	var security = new DespacheWeb.Security();
	security.enable();

});