module org.example.adg_projet {
    requires javafx.controls;
    requires com.google.gson;

    opens adg to com.google.gson;
    exports adg;

}