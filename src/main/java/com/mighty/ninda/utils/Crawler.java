package com.mighty.ninda.utils;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class Crawler {

    private final GameRepository gameRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void crawl() {
        try {
            log.info ("<< start game crawl >>");
            String connUrl = "https://store.nintendo.co.kr/games/sale";
            Document doc = Jsoup.connect(connUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

            Elements gameList = doc.getElementsByClass("category-product-item");
            int cnt = 0;
            for (Element g : gameList) {
//                if (++cnt > 3 ) {
//                    break;
//                }
                Elements title = g.getElementsByClass("category-product-item-title");
                if (isGame(title.text())) {
                    updateGame(g, title);
                    continue;
                }
                crawlGame(g, title);

                Random random = new Random();

                try {
                    Thread.sleep(random.nextInt(3000) + 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            log.info ("<< end game crawl >>");
        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void crawlSaleGame() {
        try {
            log.info ("<< start sale game crawl >>");
            String connUrl = "https://store.nintendo.co.kr/games/sale";
            Document doc = Jsoup.connect(connUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

            Elements gameList = doc.getElementsByClass("category-product-item");
            int cnt = 0;
            for (Element g : gameList) {
//                if (++cnt > 3 ) {
//                    break;
//                }
                Elements title = g.getElementsByClass("category-product-item-title");

                getSales(g, title);

                Random random = new Random();

                try {
                    Thread.sleep(random.nextInt(3000) + 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            log.info ("<< end sale game crawl >>");


        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }

    @Transactional
    private void getSales(Element g, Elements title) throws IOException {
        Optional<Game> temp_game = gameRepository.findByTitle(title.text());

        Game game;
        if (temp_game.isPresent()) {
            game = temp_game.get();
        } else {
            return;
        }

        String gameUrl = g.select("a[href]").attr("href");

        Document gameDoc = Jsoup.connect(gameUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

        Elements prices = gameDoc.getElementsByClass("special-price");
        String price = prices.select(".price").get(0).text();

        String saleDate = gameDoc.getElementsByClass("eshop-price-special_period").get(0).text();

        game.onSale(saleDate, price);
        gameRepository.save(game);
        log.info ("Add game sale info: " + game.getTitle());
    }

    @Transactional
    private void crawlGame(Element g, Elements title) throws IOException {
        Game game;
        Element price;

        if (g.getElementsByClass("price").size() > 1) {
            price = g.getElementsByClass("price").get(1);
        } else {
            price = g.getElementsByClass("price").get(0);
        }

        Elements tempDate = g.getElementsByClass("category-product-item-released");
        LocalDate releasedDate = LocalDate.of(Integer.parseInt("20" + tempDate.text().split("\\.")[0].split(" ")[1]),
                Integer.parseInt(tempDate.text().split("\\.")[1]), Integer.parseInt(tempDate.text().split("\\.")[2]));
        String imageUrl = g.select(".product-image-photo").attr("data-src");

        String gameUrl = g.select("a[href]").attr("href");

        Document gameDoc = Jsoup.connect(gameUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
        Elements descriptions = gameDoc.getElementsByClass("value").select("p");

        StringBuilder des = new StringBuilder();
        for (Element description : descriptions) {
            if (description.html().contains("닌텐도 온라인 스토어에서의 판매는 후일 실시 예정입니다.") ||
            description.html().contains("예약구입 상품입니다.")) {
                continue;
            }
            if (description.html().contains("<strong>알림</strong>")) {
                break;
            }
            des.append(description.html()).append("<br><br>");

        }

        Elements supported_languages = gameDoc.getElementsByClass("supported_languages").select(".product-attribute-val");
        game = Game.builder()
                .title(title.text())
                .price(price.text())
                .releasedDate(releasedDate)
                .imgUrl(imageUrl)
                .pageUrl(gameUrl)
                .description(des.toString())
                .language(supported_languages.text())
                .build();

        gameRepository.save(game);
        log.info ("Add game info: " + game.getTitle());
    }

    @Transactional
    private void updateGame(Element g, Elements title) throws IOException {
        Game game = gameRepository.findByTitle(title.text())
                .orElseThrow();

        Element price;

        if (g.getElementsByClass("price").size() > 1) {
            price = g.getElementsByClass("price").get(1);
        } else {
            price = g.getElementsByClass("price").get(0);
        }

        String imageUrl = g.select(".product-image-photo").attr("data-src");

        String gameUrl = g.select("a[href]").attr("href");

        Document gameDoc = Jsoup.connect(gameUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
        Elements descriptions = gameDoc.getElementsByClass("value").select("p");

        StringBuilder des = new StringBuilder();
        for (Element description : descriptions) {
            if (description.html().contains("닌텐도 온라인 스토어에서의 판매는 후일 실시 예정입니다.") ||
                    description.html().contains("예약구입 상품입니다.")) {
                continue;
            }
            if (description.html().contains("<strong>알림</strong>")) {
                break;
            }
            des.append(description.html()).append("<br><br>");

        }

        Elements supported_languages = gameDoc.getElementsByClass("supported_languages").select(".product-attribute-val");
        game.updateGame(des.toString(), imageUrl, price.text(), supported_languages.text());

        gameRepository.save(game);
        log.info ("Update game info: " + game.getTitle());
    }

    private boolean isGame(String title) {
        return gameRepository.findByTitle(title).isPresent();
    }
}
