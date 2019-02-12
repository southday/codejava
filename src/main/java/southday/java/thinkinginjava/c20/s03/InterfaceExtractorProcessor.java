package southday.java.thinkinginjava.c20.s03;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 将public方法抽取出来变成接口
 * Author southday
 * Date   2019/2/11
 */
public class InterfaceExtractorProcessor implements AnnotationProcessor {
    private final AnnotationProcessorEnvironment env;
    private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList<>();

    public InterfaceExtractorProcessor(AnnotationProcessorEnvironment env) {
        this.env = env;
    }

    @Override
    public void process() {
        for (TypeDeclaration typeDel : env.getSpecifiedTypeDeclarations()) {
            ExtractInterface annot = typeDel.getAnnotation(ExtractInterface.class);
            if (annot == null)
                break;
            for (MethodDeclaration m : typeDel.getMethods())
                if (m.getModifiers().contains(Modifier.PUBLIC)
                    && !(m.getModifiers().contains(Modifier.STATIC)))
                    interfaceMethods.add(m);
            if (interfaceMethods.size() > 0) {
                try {
                    PrintWriter writer = env.getFiler().createSourceFile(annot.value());
                    writer.println("package " + typeDel.getPackage().getQualifiedName() + ";");
                    writer.println("public interface " + annot.value() + " {");
                    for (MethodDeclaration m : interfaceMethods) {
                        writer.print("    public ");
                        writer.print(m.getReturnType() + " ");
                        writer.print(m.getSimpleName() + "(");
                        int i = 0;
                        for (ParameterDeclaration parm : m.getParameters()) {
                            writer.print(parm.getType() + " " + parm.getSimpleName());
                            if (++i < m.getParameters().size())
                                writer.print(", ");
                        }
                        writer.println(");");
                    }
                    writer.println("}");
                    writer.close();
                } catch(IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }
    }
}
