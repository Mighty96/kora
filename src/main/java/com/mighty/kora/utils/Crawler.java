package com.mighty.kora.utils;

import com.mighty.kora.domain.game.Game;
import com.mighty.kora.domain.game.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class Crawler {

    private final GameRepository gameRepository;

    public void crawl() {

        Game game = new Game();

        try {
            // 1. URL 선언
            String connUrl = "https://store.nintendo.co.kr/games";
            // 2. HTML 가져오기
            Document doc = Jsoup.connect(connUrl).get();
            // 3. 가져온 HTML Document 를 확인하기

            Elements gameList = doc.getElementsByClass("category-product-item");

            int cnt = 0;
            List<String> dbGameList = gameRepository.findAllTitle();
            for (Element g : gameList) {
                if (++cnt > 7 ) {
                    break;
                }
                Elements title = g.getElementsByClass("category-product-item-title");
                if (dbGameList.contains(title.text())) {
                    continue;
                }
                Elements price = g.getElementsByClass("price");
                Elements releasedDate = g.getElementsByClass("category-product-item-released");
                String imageUrl = g.select(".product-image-photo").attr("data-src");

                String gameUrl = g.select("a[href]").attr("href");

                Document gameDoc = Jsoup.connect(gameUrl).get();
                Elements descriptions = gameDoc.getElementsByClass("value").select("p");

                StringBuilder des = new StringBuilder();
                for (Element description : descriptions) {
                    if (description.text().contains("닌텐도 온라인 스토어에서의 판매는 후일 실시 예정입니다.")) {
                        continue;
                    }
                    des.append(description.text()).append("\n\n");

                }
                game = Game.builder()
                        .title(title.text())
                        .price(price.text())
                        .releasedDate(releasedDate.text())
                        .imgUrl(imageUrl)
                        .pageUrl(gameUrl)
                        .description(des.toString())
                        .score(0f)
                        .viewCount(0)
                        .voteCount(0)
                        .build();

                gameRepository.save(game);

                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }
    }
}
