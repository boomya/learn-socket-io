<?php foreach ($activityBanner as $k => $p) : ?>
                    <li if(<?= $k; ?> > 1) class="hidden-sm" end>
                        <a href="<?= $p['href']; ?>">
                            <span class="activity-name"><?= $p['text']; ?></span>
                            <span class="activity-pic"><img src="<?= $p['imgUrl']; ?>"></span>
                        </a>
                    </li>
                <?php endforeach; ?>