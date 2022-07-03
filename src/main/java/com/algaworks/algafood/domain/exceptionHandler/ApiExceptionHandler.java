package com.algaworks.algafood.domain.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

        private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
                        + "Tente novamente e se o problema persistir, entre em contato "
                        + "com o administrador do sistema.";
        @Autowired
        private MessageSource messageSource;

        @ExceptionHandler(EntidadeNaoEncontradaException.class)
        public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
                        WebRequest request) {

                HttpStatus status = HttpStatus.NOT_FOUND;
                String detail = ex.getMessage();
                Problem problem = createProblemBuilder(ProblemType.RECURSO_NAO_ENCONTRADA, detail, status)
                                .userMessage(detail)
                                .build();

                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(NegocioException.class)
        public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;
                String detail = ex.getMessage();
                Problem problem = createProblemBuilder(ProblemType.ERRO_NEGOCIO, detail, status)
                                .userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(EntidadeEmUsoException.class)
        public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
                HttpStatus status = HttpStatus.CONFLICT;
                String detail = ex.getMessage();
                Problem problem = createProblemBuilder(ProblemType.ENTIDADE_EM_USO, detail, status)
                                .userMessage(detail)
                                .build();

                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
                Problem problem = createProblemBuilder(ProblemType.ERRO_DE_SISTEMA, detail, status)
                                .userMessage(detail)
                                .build();

                ex.printStackTrace();

                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatus status, WebRequest request) {
                return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
        }

        @ExceptionHandler({ ValidacaoException.class })
        public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
                return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
                                HttpStatus.BAD_REQUEST, request);
        }

        private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                        HttpHeaders headers,
                        HttpStatus status, WebRequest request) {

                ProblemType problemType = ProblemType.DADOS_INVALIDOS;
                String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

                List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                                .map(objectError -> {
                                        String message = messageSource.getMessage(objectError,
                                                        LocaleContextHolder.getLocale());

                                        String name = objectError.getObjectName();

                                        if (objectError instanceof FieldError) {
                                                name = ((FieldError) objectError).getField();
                                        }

                                        return Problem.Object.builder()
                                                        .name(name)
                                                        .userMessage(message)
                                                        .build();
                                })
                                .collect(Collectors.toList());

                Problem problem = createProblemBuilder(problemType, detail, status)
                                .userMessage(detail)
                                .objects(problemObjects)
                                .build();

                return handleExceptionInternal(ex, problem, headers, status, request);
        }

        @Override
        protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                        HttpStatus status, WebRequest request) {

                if (ex instanceof MethodArgumentTypeMismatchException) {
                        return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex,
                                        headers, status,
                                        request);
                }

                return super.handleTypeMismatch(ex, headers, status, request);
        }

        private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                        HttpHeaders headers, HttpStatus status, WebRequest request) {

                String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

                Problem problem = createProblemBuilder(ProblemType.PARAMETRO_INVALIDO, detail, status)
                                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                .build();

                return handleExceptionInternal(ex, problem, headers, status, request);
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                        HttpHeaders headers, HttpStatus status, WebRequest request) {

                Throwable rootCause = ExceptionUtils.getRootCause(ex);

                if (rootCause instanceof InvalidFormatException) {
                        return handleInvalidFormaException((InvalidFormatException) rootCause, headers, status,
                                        request);
                }

                if (rootCause instanceof PropertyBindingException) {
                        return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status,
                                        request);
                }

                String detail = "O corpo da requisição ta invalida. Verifique erro de sintaxe.";
                Problem problem = createProblemBuilder(ProblemType.JSON_ERRADO, detail, status)
                                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                .build();

                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                        HttpHeaders headers, HttpStatus status, WebRequest request) {

                String path = joinPath(ex.getPath());

                String detail = String.format("A propriedade '%s' não existe. "
                                + "Corrija ou remova essa propriedade e tente novamente.", path);
                Problem problem = createProblemBuilder(ProblemType.JSON_ERRADO, detail, status)
                                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                .build();

                return handleExceptionInternal(ex, problem, headers, status, request);
        }

        private ResponseEntity<Object> handleInvalidFormaException(InvalidFormatException ex,
                        HttpHeaders headers, HttpStatus status, WebRequest request) {

                String path = joinPath(ex.getPath());

                String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                                + "que é do um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                                path, ex.getValue(), ex.getTargetType().getSimpleName());
                Problem problem = createProblemBuilder(ProblemType.JSON_ERRADO, detail, status)
                                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                .build();

                return handleExceptionInternal(ex, problem, headers, status, request);
        }

        private String joinPath(List<Reference> references) {
                return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
        }

        @Override
        protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                        HttpStatus status, WebRequest request) {
                String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                                ex.getRequestURL());

                Problem problem = createProblemBuilder(ProblemType.RECURSO_NAO_ENCONTRADA, detail, status).build();

                return handleExceptionInternal(ex, problem, headers, status, request);
        }

        @Override
        protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                        HttpStatus status, WebRequest request) {

                if (body == null) {
                        body = Problem.builder()
                                        .title(status.getReasonPhrase())
                                        .status(status.value())
                                        .timestamp(OffsetDateTime.now())
                                        .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                        .build();
                } else if (body instanceof String) {
                        body = Problem.builder()
                                        .title((String) body)
                                        .status(status.value())
                                        .timestamp(OffsetDateTime.now())
                                        .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                                        .build();
                }

                return super.handleExceptionInternal(ex, body, headers, status, request);
        }

        private Problem.ProblemBuilder createProblemBuilder(ProblemType type, String detail, HttpStatus status) {
                return Problem.builder()
                                .title(type.getTitle())
                                .status(status.value())
                                .type(type.getUri())
                                .timestamp(OffsetDateTime.now())
                                .detail(detail);
        }
}
