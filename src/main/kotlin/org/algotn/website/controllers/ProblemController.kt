package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.algotn.api.problem.ProblemType
import org.algotn.api.utils.slugify
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import java.util.*


@Controller
class ProblemController {

    companion object {

        val problems = mutableMapOf<String, Problem>()

        init {
            val name = "Problème basique"
            problems[name.slugify()] = Problem(
                name = name,
                type = ProblemType.PASS_FAIL,
                "C'est pas très compliqué donc essayez de réussir...",
                "Une ligne contenant une chaîne de caractère",
                "La même ligne",
                "Temps maximal d'exécution : 1s<br>" +
                        "Quantité de mémoire maximale : 100 MB",
                keywords = arrayOf("graphe", "dijkstra"),
                /*listOf(
                    Example(
                        UUID.randomUUID(),
                        "Hello world",
                        "Hello world",
                        "Est-il nécessaire d'expliquer ?"
                    )
                ),
                listOf(
                    InputOutputTest(
                        listOf("abc", "Je suis une patate", "Moi je suis un concombre"),
                        listOf("abc", "Je suis une patate", "Moi je suis un concombre")
                    )
                ),
                mapOf()*/
            )
/*
            problems["test1"] = Problem(
                UUID.randomUUID(),
                "Strange doors",
                "Dans votre exploration de ruines interstellaires vous arrivez devant une porte fermée,vous observez un petit moniteur devant celle-ci il y est inscrit un nombre H ainsi qu'une liste L de longueur N d'entier K.\n" +
                        "Pour déverrouiller la porte vous devez renvoyer la somme des diviseur de H contenu dans L ayant exactement 2 diviseurs premier.",
                "Sur la premiere ligne \\(0 \\le N \\le 10^5\\) la longueur de la liste<br>" +
                        "Sur la deuxième ligne \\(1 \\le H \\le 10^9\\) le nombre dont on cherche les diviseurs <br>" +
                        "Sur la ligne 3, N entiers \\(1 \\le K \\le 10^3\\) séparés par des espaces",
                "Un entier égal à la somme des diviseurs de H dans L ayant exactement 2 diviseurs premier.",
                "Temps maximal d'exécution : 1s<br>" +
                        "Quantité de mémoire maximale : 100 MB",
                listOf(
                    Example(
                        UUID.randomUUID(),
                        "5<br>" +
                                "9<br>" +
                                "0 1 3 5 7",
                        "0",
                        "Le seul diviseur de 9 ayants exactement 2 diviseurs premiers est 9,<br>" +
                                "il n'y a pas de 9 donc la somme vaut 0"
                    ),
                    Example(
                        UUID.randomUUID(),
                        "10<br>" +
                                "45<br>" +
                                "1 8 3 9 15 9 18 46 20 45",
                        "33",
                        "Les seul diviseurs de 45 ayants exactement 2 diviseurs premiers sont 9 et 15,<br>" +
                                "il y a 2 fois 9 et 1 fois 15 donc une somme égale à 33."
                    )
                ),
                listOf(
                    InputOutputTest(
                        listOf("10\n" + "45\n" + "1 8 3 9 15 9 18 46 20 45","673\n" +
                                "38544\n" +
                                "854 690 82 617 511 479 188 597 794 748 629 941 36 789 479 418 121 872 445 943 66 586 274 128 877 110 313 554 445 78 79 761 912 590 441 984 135 790 843 265 545 476 762 391 626 944 462 396 372 735 313 718 502 292 438 288 441 621 515 794 917 4 928 209 113 343 155 623 929 722 765 914 168 814 945 14 474 932 213 852 673 327 173 914 255 416 416 872 748 983 349 623 917 799 99 169 225 912 395 243 483 63 248 207 114 912 676 117 999 268 308 812 921 925 489 443 795 295 953 305 210 566 314 136 15 665 639 781 48 94 247 42 962 733 423 476 329 343 789 607 681 347 591 14 260 989 625 639 736 990 414 134 736 425 6 732 626 212 166 606 423 257 884 277 730 852 3 185 828 518 606 648 505 841 183 272 406 101 751 200 881 29 596 513 621 388 486 295 601 962 16 412 659 549 523 270 747 240 935 709 62 819 5 46 153 248 777 847 612 874 522 799 940 529 428 670 381 873 917 337 415 915 250 351 159 448 896 410 399 438 340 167 143 661 397 924 639 174 467 552 728 501 688 739 759 270 587 810 488 641 201 426 617 299 405 328 530 435 201 759 464 98 95 549 897 82 901 237 945 36 873 321 882 238 869 673 286 97 379 907 542 662 280 32 826 469 16 389 327 476 530 578 769 561 434 582 952 120 724 233 363 594 371 615 951 872 155 63 321 247 590 408 351 248 611 171 188 396 870 951 893 538 723 820 338 639 90 168 473 131 645 377 307 322 2 129 99 691 610 880 714 373 227 43 169 360 832 33 304 140 787 296 1 454 182 713 582 774 598 644 25 600 92 237 230 680 604 112 555 113 88 959 2 618 86 928 966 600 531 84 260 59 566 201 911 711 464 553 544 432 298 371 339 383 304 769 145 61 755 855 785 886 692 161 751 30 942 532 915 861 254 78 640 234 81 495 763 878 746 125 497 801 823 153 241 88 9 32 931 259 855 225 701 48 890 708 191 397 706 72 235 516 653 461 461 696 409 736 322 422 375 901 395 256 741 955 617 941 746 41 231 227 308 651 85 912 644 604 708 925 298 572 612 731 708 682 566 731 797 46 411 755 229 222 519 547 207 256 289 404 887 560 957 314 464 160 407 455 625 322 668 375 254 955 705 800 275 434 681 39 367 333 560 59 388 356 422 69 699 434 494 621 61 313 959 881 792 884 149 317 589 26 918 319 516 84 966 383 993 952 481 144 207 499 674 862 34 611 926 340 266 777 612 847 714 439 323 853 398 25 564 104 616 100 651 199 96 657 582 158 458 836 94 971 936 281 969 564 405 669 219 885 739 424 44 918 399 658 902 902 351 146 691 388 752 899 898 123 352 684 698 334 117 217 237 209 732 337 375 792 998 3 211 138 324 364 735 990 105 244 363 736 552 722 645 472 335 94 946 373 655 550 556 483 308 918 35 763 954 485 575 685 423 645 595 867 892 324 115 26 389 624 825 512 614 57 472 587 760 268 719 411 160 366 81 206 912 473 549 927 234 231 958"),
                        listOf("33","408")
                    )
                ),
                mapOf()
            )

            problems["test2"] = Problem(
                UUID.randomUUID(),
                "Problem Name (test)",
                "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\) and they are\n" +
                        "\$\$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.\$\$ (test)",
                "Problem input (test)",
                "Problem output (test)",
                "Problem exec_c (test)",
                listOf(Example(UUID.randomUUID(), "0<br>1 2", "2", "")),
                listOf(),
                mapOf()
            )*/
            val pbMap = Chili.getRedisInterface().client.getMap<String, String>("problem")
            for (key in pbMap.keys){
                problems.put(key,Gson().fromJson(pbMap.get(key),Problem::class.java))
            }
        }
    }

    @GetMapping("/problem/{id}")
    fun lookProblem(
        @PathVariable("id") id: String, model: Model
    ): ModelAndView {
        if (!problems.containsKey(id)) {
//            val pbMap = Chili.getRedisInterface().client.getMap<String, String>("problem")
//            val newPbJson = pbMap.getOrDefault(id,"")
//            if (newPbJson=="") {
//                return ModelAndView("redirect:/")
//            }else{
//                val newPb = Gson().fromJson(newPbJson,Problem::class.java)
//                problems.put(newPb.name,newPb)
//            }
            return ModelAndView("redirect:/")

        }
        val problem = problems[id]
        model.addAttribute("problem", problem)
        return ModelAndView("problem")
    }
}