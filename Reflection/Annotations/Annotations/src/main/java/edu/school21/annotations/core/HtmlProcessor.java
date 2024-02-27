package edu.school21.annotations.core;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "edu.school21.annotations.core.HtmlForm",
        "edu.school21.annotations.core.HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> formClasses = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element formClass : formClasses) {
            GenerateHtmlFile(formClass);
        }
        return true;
    }

    private void GenerateHtmlFile(Element formClass) {
        String fileName = formClass.getAnnotation(HtmlForm.class).fileName();

        try (PrintWriter out = new PrintWriter(processingEnv.getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT, "", fileName)
                .openWriter())) {

            String content = GenerateHtmlContent(formClass);

            out.print(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String GenerateHtmlContent(Element formClass) {
        String formOpenTag = "<form action = \"?\" method = \"?\">\n"
                .replaceFirst("\\?", formClass.getAnnotation(HtmlForm.class).action())
                .replaceFirst("\\?", formClass.getAnnotation(HtmlForm.class).method());

        StringBuilder inputs = new StringBuilder();
        String inputFieldTemplate = "\t<input type = \"?\" name = \"?\" placeholder = \"?\">\n";
        String submit = "\t<input type = \"submit\" value = \"Send\">\n";
        String formCloseTag = "</form>\n";

        List<? extends Element> fields = formClass.getEnclosedElements().stream()
                .filter(element -> element.getKind().isField())
                .filter(field -> field.getAnnotation(HtmlInput.class) != null)
                .collect(Collectors.toList());

        for (Element field : fields) {
            HtmlInput annotation = field.getAnnotation(HtmlInput.class);
            String inputField = inputFieldTemplate.replaceFirst("\\?", annotation.type())
                    .replaceFirst("\\?", annotation.name())
                    .replaceFirst("\\?", annotation.placeholder());
            inputs.append(inputField);
        }

        return formOpenTag + inputs + submit + formCloseTag;
    }
}
