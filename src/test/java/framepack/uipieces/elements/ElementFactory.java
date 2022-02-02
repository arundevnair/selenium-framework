package framepack.uipieces.elements;

import org.openqa.selenium.WebElement;

public interface ElementFactory {
    <E extends Element> E create(Class<E> elementClass,  WebElement customWebElement);
}
