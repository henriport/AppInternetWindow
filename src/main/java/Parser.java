import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    String day;
    String month;
    double usd;
    double eur;
    double ratio;

    public void workParser() throws Exception {
        Document page = getPage();

        calculateDay(page);
        calculateMonth(page);
        calculateUsd(page);
        calculateEur(page);
        calculateRatio();
    }

    private void calculateDay(Document page) throws Exception {
        Element dayElem = page.selectFirst("span[class=datetime__day]");
        setDay(getValueFromString2(dayElem.toString()));
    }

    private void calculateMonth(Document page) throws Exception {
        String month;
        Element monthElem = page.selectFirst("span[class=datetime__month]");
        month = monthElem.selectFirst("span[class=datetime__month]").text();
        setMonth(month.substring(0, month.length() - 1));
    }

    private void calculateUsd(Document page) throws Exception {
        Elements listOfValues = page.select("span[class=inline-stocks__value_inner]");
        String value;
        value = getValueFromString(listOfValues.eq(0).toString());
        setUsd(Double.parseDouble(value.replaceAll("[,]", ".")));
    }

    private void calculateEur(Document page) throws Exception {
        Elements listOfValues = page.select("span[class=inline-stocks__value_inner]");
        String value;
        value = getValueFromString(listOfValues.eq(1).toString());
        setEur(Double.parseDouble(value.replaceAll("[,]", ".")));
    }

    private void calculateRatio() {
        setRatio(eur / usd);
    }

    private void setDay(String day) {
        this.day = day;
    }

    private void setMonth(String month) {
        this.month = month;
    }

    private void setUsd(double usd) {
        this.usd = usd;
    }

    private void setEur(double eur) {
        this.eur = eur;
    }

    private void setRatio(double ratio) {
        System.out.println("ratio=" + ratio);
        this.ratio = ratio;
    }

    public static Document getPage() throws IOException {
        String url = "https://yandex.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public double getUsd() {
        return usd;
    }

    public double getEur() {
        return eur;
    }

    public double getRatio() {
        return ratio;
    }

    //<span class="inline-stocks__value_inner">63,82</span>
    //63,82
    private static Pattern pattern = Pattern.compile("\\d{2}\\,\\d{2}");
    private static Pattern pattern2 = Pattern.compile("\\d{1}");//1 для чисел от 1 до 9, 2 - от 10 до 31

    public static String getValueFromString(String stringValue) throws Exception {
        Matcher matcher = pattern.matcher(stringValue);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't find");
    }

    public static String getValueFromString2(String stringValue) throws Exception {
        Matcher matcher = pattern2.matcher(stringValue);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't find");
    }

}
