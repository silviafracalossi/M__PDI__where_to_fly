

/*
public static void main(String[] args) {
        // configure spark
        SparkConf sparkConf = new SparkConf().setAppName("Spark RDD filter")
                .setMaster("local[2]")
                .set("spark.executor.memory", "2g");
        // start a spark context
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        // read list to RDD
        List<String> data = Arrays.asList("Learn", "Apache", "Spark", "with", "Tutorial Kart");
        JavaRDD<String> words = sc.parallelize(data, 1);

        // map each word to -> (word, word length)
        JavaPairRDD<String, Integer> wordsRDD = words.mapToPair(s -> new Tuple2<>(s, s.length()));

        // filter : where the second element in tuple is equal to 5. (i.e., word length == 5)
        Function<Tuple2<String, Integer>, Boolean> filterFunction = w -> (w._2 == 5);

        // apply the filter on wordsRDD
        JavaPairRDD<String, Integer> rddf = wordsRDD.filter(filterFunction);

        // print filtered rdd
        rddf.foreach(item -> {
            System.out.println(item);
        });

        sc.close();
    }*/
