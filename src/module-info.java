module org.example.adg_projet {
    requires javafx.controls;
    requires org.objectweb.asm;
    requires com.google.gson;

    exports adg;

    opens adg to com.google.gson;
}