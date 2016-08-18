package com.tstasks.sanchellios.navicostores.translit_handling;

/**
 * Created by alex on 18.08.16.
 */
public class NavicoTransliterator extends Transliterator {
    public static String getRussianString(String translit){
        String updatedString = translit.replaceAll("Ts", "Ц")
                .replaceAll("ts", "ц")
                .replaceAll("Ya", "Я")
                .replaceAll("ya", "я")
                .replaceAll("Ta", "Тя")
                .replaceAll("ta", "тя")
                .replaceAll("Ny", "Ный")
                .replaceAll("ny", "ный")
                .replaceAll("Sound", "Саунд")
                .replaceAll("sound", "саунд");
        return transliterate(updatedString);
    }
}
