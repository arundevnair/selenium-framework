package framepack.uipieces.elements;

import org.openqa.selenium.WebElement;

public interface ContainerFactory {
    <C extends Container> C create(Class<C> containerClass, WebElement wrappedElement);
}
