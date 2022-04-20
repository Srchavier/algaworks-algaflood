package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorFiled;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorFiled = constraintAnnotation.valorFiled();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean isValid = true;

  
            try {
                BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorFiled)
                        .getReadMethod().invoke(objetoValidacao);
                String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
                        .getReadMethod().invoke(objetoValidacao);

                if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                    isValid = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
                }
            } catch (Exception e) {
                throw new ValidationException(e);
            } 
            
   

        return isValid;
    }

}
