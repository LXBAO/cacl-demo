package com.example.test;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.example.crawler.vo.WeiXinExportVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

    public static <T> T getProxy(Class<T> tClass) {
        //newProxyInstance 创建代理对象
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), tClass.getInterfaces(), new InvocationHandler() {
            @Override
            //InvocationHandler 对目标方法进行拦截
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //前置增强
                Object obj = method.invoke(tClass.newInstance(), args);
                //后置增强
                return obj;
            }
        });
    }

    //元音字母反转
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        //二分算法
        while (left < right) {
            //如果左右同时满足
            if (isVowel(s[left]) && isVowel(s[right])) {
                char temp = s[left];
                s[left] = s[right];
                s[right] = temp;
                left++;
                right--;
            } else if (isVowel(s[right])) {
                left++;
            } else if (isVowel(s[left])) {
                right--;
            } else {
                left++;
                right--;
            }
        }
    }

    private boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            return true;
        }
        return false;
    }

    public static boolean checkInclusion(  ) {
        String s1 = "adc", s2 = "dcda";
        int s1L = s1.length(),s2L = s2.length();
        if (s1L > s2L){
            return false;
        }

        /*int []tag = new int[s1L];
        int []aa = new int[s1L];
        for(int i=0;i<s1L;i++){
            tag[i] = s1.charAt(i);
            aa[i] = s2.charAt(i);
        }

        int  [] agt = sort(tag);
        int l=s1L;
        while (l<s2L){
            int [] resultArr = sort(aa);
            if(Arrays.equals(agt,resultArr)){
                return true;
            }else{
               aa[l%s1L]=s2.charAt(l);
            }
            l++;
        }*/

        return false;
    }


    private  static  int []  sort(int []arr){
        int [] resultArr = Arrays.copyOf(arr,arr.length);
        for(int i=0;i<resultArr.length;i++){
            for(int j=i+1;j<resultArr.length;j++){
                if(resultArr[i] > resultArr[j]){
                    int temp = resultArr[j];
                    resultArr[j] =  resultArr[i];
                    resultArr[i] = temp;

                }
            }
        }
        return resultArr;
    }

    public static int[][] floodFill(int[][] image, int sc, int color) {
        int l = 0;
        int right = image.length-1;
        while(l <= right){
            check(image[l],sc,color);
            l++;
            if(l <= right){
                check(image[right],sc,color);
                right--;
            }

        }
        return image;
    }

    private static void check(int[] image,   int sc, int color){
        int last = 0;
        for(int i=0;i<image.length;i++){
            boolean bool = false;
            if(i==0){
                bool =true;
            }else if(image[i]==sc && image[i]==last){
                bool =true;

            }
            last = image[i];
            if(bool){
                image[i] =  color;
            }
            System.out.println(image[i]);
        }

    }


    public static int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        //i 表示行，j表示列
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    //取出最大的值
                    res = Math.max(res, dfs(i, j, grid));
                }
            }
        }
        return res;
    }
    private static int dfs(int i, int j, int[][] grid) {
        //当超出岛屿边界（上下左右）和遇到海洋的时候，停止搜索相邻的格子，
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        //将陆地改为海洋，防止重复陆地重复遍历。
        grid[i][j] = 0;
        //每遍历一次陆地就加一
        int num = 1;
        //当前格子的上一格
        num += dfs(i + 1, j, grid);
        //当前格子的下一格
        num += dfs(i - 1, j, grid);
        //当前格子右一格
        num += dfs(i, j + 1, grid);
        //当前格子的左一格
        num += dfs(i, j - 1, grid);
        return num;

    }

    private static void sortKM(int [] arr,int left,int right){
        if(left >= right){
            return;
        }
        // 5 2 4  1 3
        int base = arr[right];
        int k = left;
        //快慢指针排序：根据基准值，把小于基准值的放左侧，大于基准值的放右测，
        // 再重新选举，左边和左边比较交换，右边和右边比较交换

        for(int i=left;i<right;i++){
            if(arr[i] < base){
                if(k != i){
                    int temp = arr[i];
                    arr[i] = arr[k];
                    arr[k] = temp;
                }
                k++;
            }

        }

        int temp = arr[k];
        arr[k] =  arr[right];
        arr[right] =  temp;
        sortKM(arr,left,k-1);
        sortKM(arr,k+1,right);
    }

    private static void sortKP(int [] arr,int left,int right){
        if(left >= right){
            return;
        }
        // 5,2,4,3
        int base = arr[left];
        int i=left,j = right;
        while ( i< j ){
            while (i< j && arr[j] >= base){
                j--;
            }

            arr[i] = arr[j];
            while (i < j && arr[i] <= base){
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i]  = base;
        sortKP(arr,left,i);
        sortKP(arr,i+1,right);
    }
  static volatile   int count = 0;

    /**
     * 创建实体模型
     */
    public static void  createEntity(){

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test","root","root").
                globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride().outputDir("c:\\data\\java"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")).pathInfo(Collections.singletonMap(OutputFile.xml, "c:\\data\\java\\mapper")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.likeTable(new LikeTable("t_", SqlLike.DEFAULT)))
                .strategyConfig((scanner, builder) -> builder.controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok()
                        .versionColumnName("version") // 基于数据库字段
                        .versionPropertyName("version")// 基于模型属性
                        .addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())

                .execute();
    }

    public static void main(String[] args) throws InterruptedException {



        //createEntity();


        /*System.out.println(System.currentTimeMillis() / 1000);;
        int arr[] = {5,2,4,3};
        sortKP(arr,0,arr.length-1);
        System.out.println(arr);*/
      /*  int aa[][] =  { {0,0,1,0,0,0,0,1,0,0,0,0,0 }, {0,0,0,0,0,0,0,1,1,1,0,0,0 }, {0,1,1,0,1,0,0,0,0,0,0,0,0 }, {0,1,0,0,1,1,0,0,1,0,1,0,0 }, {0,1,0,0,1,1,0,0,1,1,1,0,0 }, {0,0,0,0,0,0,0,0,0,0,1,0,0 }, {0,0,0,0,0,0,0,1,1,1,0,0,0 }, {0,0,0,0,0,0,0,1,1,0,0,0,0 } };
        maxAreaOfIsland(aa);
        int [][] image = {{0,0,0},{0,0,0}};
        int sr = 1, sc =0, newColor = 2;
        floodFill(image,sc,newColor);
        // 第一次交换 1,0,2,0,3,12
        // 第二次交换 1,2,0,0,3,12
        // 第三次交换 1,2,3,0,0,12
        // 第四次交换 1,2,3,12,0,0
        String s = "abcdefg";

        s.indexOf("cd");
        int dp = 0;//记录相同字符开始的下标
        // 计算两个相同下标差
        Map<Character, Integer> map = new HashMap<>();
        char[] charArr = s.toCharArray();
        int res = 0;
        for (int i = 0; i < charArr.length; i++) {
            Integer repeatIdx = map.get(charArr[i]);
            if (repeatIdx != null && repeatIdx >= dp) {
                dp = repeatIdx;
            }
            res = Math.max(res, i - dp);
            map.put(charArr[i], i);
        }




    StringBuffer ret = new StringBuffer();

    int length = s.length();
    int i = 0;
        while(i<length)

    {
        int start = i;
        while (i < length && s.charAt(i) != ' ') {//遍历单词，用i计数
            i++;
        }

        for (int p = 0; p < i - start; p++) {
            ret.append(s.charAt(i - (1 + p)));//注意此处反转单词的公式

        }
        if (i < length && s.charAt(i) == ' ') {
            i++;//遇到空格计数+1
            ret.append(' ');//把空格加入字符串中
        }

    }*/


    //int[] arr = new int[]{2, 8, 5, 7, 1, 3, 4, 9, 6, 10};
        /*for (int i = 0; i < nums.length; i++) {
            //当前元素!=0，就把其交换到左边，等于0的交换到右边
            //注意j要++，不然先前的值就会被覆盖
            if (nums[i] != 0){
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j++] = temp;
            }
        }*/

    // A a =   getProxy(B.class);
    //a.test("222");
      /*  Cglib cglib = new Cglib();
        B b = (B)cglib.getProxy(B.class);
        b.test("2222");*/

        /*for(int i=0;i<arr.length;i++){
            ++count;
            for(int j=0;j<arr.length;j++){
                ++count;
            if(arr[i] < arr[j]){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            }
        }*/

     /*   for (int i = 0; i < arr.length-1; i++) {//外循环控制循环的轮数
            ++count;
            for (int j = i+1 ; j < arr.length; j++) {//内循环控制每一轮被比较的元素
                ++count;
                if(arr[i] > arr[j]){
                     int temp = arr[j];
                     arr[j] =  arr[i];
                     arr[i] = temp;

                 }

            }
        }*/

    // quicksort(arr, 0, arr.length - 1);
    A a = A.B;
    System.out.println(a.getNum() +"---"+a.getStr());
    R<Page<WeiXinExportVO>> pageR = new R<>();
    Page<WeiXinExportVO> page = new Page<>();
    R<WeiXinExportVO> pageR2 = new R<>();
    WeiXinExportVO weiXinExportVO = new WeiXinExportVO();
        weiXinExportVO.setLike_num(1);
        weiXinExportVO.setOld_like_num(2);
        weiXinExportVO.setRead_num(3);
        page.setEntity(weiXinExportVO);
        pageR.setName("LX");
        pageR.setTotal_size(100);
        pageR.setT(page);
        pageR2.setT(weiXinExportVO);
        pageR2.setTotal_size(50);
        pageR2.setName("LX2");
        //System.out.println(page);

        /*ThreadLocal<Byte[]> test = new ThreadLocal<>();
         new Thread(()-> {
             while (true){
                 String a = "aaaa";
             }
         },"t1" ).start();

        new Thread(()-> {
            while (true){
                String b = "bbbb";
            }
        },"t2" ).start();*/

        /*ThreadPoolExecutor threadPoolExecutor =new ThreadPoolExecutor(1000,2000,5000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10000));
        while(true){
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    test.set(new Byte[1024]);
                }
            });
        }*/
    }

    public static void quicksort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        //保存基数
        int basic = arr[left];
        //定义左右指针
        int i = left;
        int j = right;

        while (i < j) {

            while (i < j && arr[j] > basic) {//操作右指针找到小于基数的下标

                j--;

            }
            if (i < j) {
                arr[i] = arr[j];    //将右指针对应小于基数的值放到左指针所指的位置
                i++;                //左指针自加
            }
            while (i < j && arr[i] < basic) {//相反，找到大于基数的下标

                i++;

            }
            if (i < j) {
                arr[j] = arr[i];    //大于基数的值赋给右指针所指的位置
                j--;                //右指针自减
            }
        }
        arr[i] = basic;                //将基数放入到指针重合处
        quicksort(arr, left, i - 1);    //重复调用，对左半部分数组进行排序
        quicksort(arr, i + 1, right);    //对右半部分数组进行排序
    }


}
