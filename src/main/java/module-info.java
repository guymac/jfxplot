/**
 * Defines a module, required for jlink.
 */

module guymac
{
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens guymac;
}