package kg.akahagroup.tobokfest.enums;

public enum Oblasts {

    CHUI("Чуйская область"),
    NARYN("Нарынская область"),
    ISSYK_KUL("Иссык-Кульская область"),
    OSH("Ошская область"),
    TALAS("Таласская область"),
    BATKEN("Баткенская область"),
    JALAL_ABAD("Джалал-Абадская область");

    private final String label;

    Oblasts(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
