package framepack.uipieces.elements;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;



public class ExtendedFieldDecorator extends DefaultFieldDecorator {
    private ElementFactory elementFactory = new DefaultElementFactory();
    private ContainerFactory containerFactory = new DefaultContainerFactory();

    public ExtendedFieldDecorator(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {
        if (Container.class.isAssignableFrom(field.getType())) {
            return decorateContainer(loader, field);
        }
        if (Element.class.isAssignableFrom(field.getType())) {
//            System.out.println(field.getName());
            Object element = decorateElement(loader, field);
            return element;
        }
        return super.decorate(loader, field);
    }

    private Object decorateElement(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        return elementFactory.create((Class<? extends Element>) field.getType(), wrappedElement);
    }

    private ElementLocator createLocator(final Field field) {
        return factory.createLocator(field);
    }

    private Object decorateContainer(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        final Container container = containerFactory.create((Class<? extends Container>) field.getType(), wrappedElement);

        PageFactory.initElements(new ExtendedFieldDecorator(wrappedElement), container);
        return container;
    }

}

