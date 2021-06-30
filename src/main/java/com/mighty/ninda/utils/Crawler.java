package com.mighty.ninda.utils;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class Crawler {

    private final GameRepository gameRepository;

    public void crawl() {

        Game game = new Game();

        try {
            String connUrl = "https://store.nintendo.co.kr/games";
            Document doc = Jsoup.connect(connUrl).timeout(30000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

            Elements gameList = doc.getElementsByClass("category-product-item");

            for (Element g : gameList) {
//                if (++cnt > 6 ) {
//                    break;
//                }
                Elements title = g.getElementsByClass("category-product-item-title");
                if (findByTitle(title.text())) {
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


        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }

    @Transactional
    private void crawlGame(Element g, Elements title) throws IOException {
        Game game;
        Elements price = g.getElementsByClass("price");
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
                .reLike(0)
                .reHate(0)
                .likeList("")
                .hateList("")
                .viewCount(0)
                .language(supported_languages.text())
                .build();

        gameRepository.save(game);
    }

    @Transactional
    private boolean findByTitle(String title) {
        if (gameRepository.findByTitle(title).isPresent()) {
            return true;
        }

        return false;
    }
}
