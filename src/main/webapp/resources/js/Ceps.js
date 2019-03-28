function getCEP() {  
	if($.trim($("#cep").val()) != ""){  
        $.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+$("#cep").val(), function(){  
            if(resultadoCEP["resultado"] == 1){  
              $("#endereco").val(unescape(resultadoCEP["logradouro"]));  
              //  $("#endereco").val(unescape(resultadoCEP["tipo_logradouro"]));  
              $("#bairro").val(unescape(resultadoCEP["bairro"]));  
              $("#cidade").val(unescape(resultadoCEP["cidade"]));  
              $("#estado").val(unescape(resultadoCEP["uf"]));  
            }else{  
                alert("Endereço não encontrado para o cep ");  
            }  
        });  
    }  
}  