package com.example.testcase;



import java.util.*;

public class test1 {
    private static final int M1 = 0x55555555;
    private static final int M2 = 0x33333333;
    private static final int M4 = 0x0f0f0f0f;
    private static final int M8 = 0x00ff00ff;
    private static final int M16 = 0x0000ffff;

    static String[] thousands = {"", "M", "MM", "MMM"};
    static String[] hundreds  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    static String[] tens      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    static String[] ones      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    /*static Map<Character,String> map = new HashedMap(){{
       *//* map.put();
        put('3',"def");
        put('4',"ghi");
        put('5',"jkl");
        put('6',"mno");
        put('7',"pqrs");
        put('8',"tuv");
        put('9',"wxyz");*//*
    }};*/


    public static int[] searchRange( ) {
        int [] nums = {5,7,7,8,9};int target = 8;
        int len = nums.length, left = 0, right = len - 1;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        if(left >= len || nums[left] != target){
            return new int[]{-1, -1};
        }else {
            int end =left;
            for(int i=left+1;i<len;i++){
                if(nums[i] > nums[left]){
                    break;
                }
                end = i;
            }
            return new int[]{left, end};
        }

    }

    public static int search(int[] nums, int target) {
        int len = nums.length, left = 0, right = len - 1;
        while (left < (right+1)){
            if(nums[left] == target){
                return left;
            }
            if(nums[right] == target){
                return right;
            }
            left++;
            right--;
        }
        return -1;
    }

    public static boolean searchMatrix( ) {
        int[][] matrix   = {{1}};
        int  target=1;
        int len = matrix.length,detLen = matrix[0].length;
        int start = binarySearchFirstColumn(matrix,target, len-1);
        if(start <0){
            return false;
        }
        int []nums = matrix[start];
        int l= 0,r = detLen-1;
        while (l <= r){
            if(nums[l] == target){
                return true;
            }
            if(nums[r] == target){
                return true;
            }
            l++;
            r--;
        }
        return false;
    }

    public static int binarySearchFirstColumn(int[][] matrix, int target,int right){
        int left = -1;
        while (left < right){
            int mid = (left+right+1) >> 1;
            if(matrix[mid][0] <= target){
                left = mid;
            }else {
                right = mid;
            }
        }
        return left;
    }

    public static int findMin(int[] nums) {
        int len = nums.length,l=0,r=len-1;
        int result = nums[0];
        while (l < r+1){
            if(nums[l] > nums[r]){
                result = Math.min(result,nums[r]);
            }else {
                result = Math.min(result,nums[l]);
            }
           l++;
           r--;
        }
        return result;
    }
    public static int findPeakElement(int[] nums) {
        int len = nums.length,left =1,right = len-1, max =nums[0],index = 0;
        while (left < right+1){
            if(nums[left] > max){
                max = nums[left];
                index = left;
            }
            if(nums[right] > max){
                max = nums[right];
                index = right;
            }
            left++;
            right--;
        }
        return index;
    }

    public static Node connect() {
        //1,2,3,4,5,null,7
        Node root7 = new Node(7,null,null,null);
        Node root5 = new Node(5,null,null,null);
        Node root4 = new Node(4,null,null,null);
        Node root3 = new Node(3,null,root7,null);
        Node root2 = new Node(2,root4,root5,null);
        Node root = new Node(1,root2,root3,null);
        if (root == null)
            return root;
        //cur我们可以把它看做是每一层的链表
        Node cur = root;
        while (cur != null) {
            //串联节点
            Node dummy = new Node(0);
            //记录当前节点
            Node pre = dummy;
            //然后开始遍历当前层的链表
            while (cur != null) {
                if (cur.left != null) {
                    //如果当前节点的左子节点不为空，就让pre节点
                    //的next指向他，也就是把它串起来
                   pre =  pre.next = cur.left;
                }
                //同理参照左子树
                if (cur.right != null) {
                   pre =  pre.next = cur.right;
                }
                //继续访问这一行的下一个节点
                cur = cur.next;
            }
            //把下一层串联成一个链表之后，让他赋值给cur，
            //后续继续循环，直到cur为空为止
            cur = dummy.next;
        }
        return root;
    }
    //List<List<Integer>> lists = new ArrayList<>();
    public static void connect(Node root ){
        while (root != null) {
            if (root.left != null) {
                root.next = root.right;
            }
           /* if (root.right != null) {
                root.next = root.right;
            }*/
        }
            //connect(root.left,root.right);
    }
    public static boolean isSubtree( ) {
       /* TreeNode subRoot1 = new TreeNode(2,null,null);
        TreeNode subRoot2 = new TreeNode(1,null,null);
        TreeNode subRoot3 = new TreeNode(1,subRoot2,subRoot1);
        TreeNode subRoot4 = new TreeNode(1,null,subRoot3);*/
        TreeNode subRoot = new TreeNode(1,null,null);
       /* TreeNode root9 = new TreeNode(2,null,null);
        TreeNode  root8 = new TreeNode(1,null,null);
        TreeNode root7 = new TreeNode(1,root8,root9);
        TreeNode root6 = new TreeNode(1,null,root7);
        TreeNode root5 = new TreeNode(1,null,root6);
        TreeNode root4 = new TreeNode(1,null,root5);*/
        TreeNode root3 = new TreeNode(1,null,null);
        TreeNode root = new TreeNode(1,root3,null);
       /* TreeNode subRoot = new TreeNode(1,null,null);
        //TreeNode root1 = new TreeNode(1,null,null);
        TreeNode root = new TreeNode(1,null,null);*/
        List<Integer> rList = new ArrayList<>();
        List<Integer> tList = new ArrayList<>();

        getDfsOrder(subRoot,tList);

        getDfsOrder(root,rList);
        if(tList.size() == rList.size()
                && rList.get(0).intValue() == tList.get(0).intValue()){
            return true;
        }
        boolean bool = check(rList,rList.size(),tList, tList.size());
        return false;
    }
    public static boolean check( List<Integer> rList, int size, List<Integer> tList,int subSize){
        for(int i=0;i<size;i++){
            int r = i,s=0;
            while (s < subSize){
                if(rList.get(r).intValue() != tList.get(s).intValue()){
                   break;
                }
                r++;
                s++;
            }
            if(s == subSize){
                return true;
            }
        }
        return false;
        /*if(j == tList.size() ){
            return true;
        }else{
            if(rList.size() >= start+tList.size()){
                return check(++start,  rList,  tList);
            }else {
                return false;
            }
        }*/

    }
    //Integer def = null;
    static int def =999;
    public static void getDfsOrder(TreeNode t, List<Integer> tar){
        if(t == null){
            return;
        }

        tar.add(t.val);
        if(t.left != null){
            getDfsOrder(t.left,tar);
        }else {
            tar.add(def);
        }

        if(t.right != null){
            getDfsOrder(t.right,tar);
        }else {
            tar.add(def);
        }
    }


    public static int shortestPathBinaryMatrix() {
        int[][] grid = {{0,0,0},{1,1,0},{1,1,0}};
        int[][] dir = new int[][]{{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,1},{1,-1}};
        int len = grid.length,dirLen = dir.length,dis = 0;
        if(grid[0][0] !=0 || grid[len-1][len-1] !=0){
            return -1;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        grid[0][0] = 1;

        while (!queue.isEmpty()) {
            dis++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                int code = queue.poll();
                int x = code / len;
                int y = code % len ;
                if (x == len - 1 && y == len - 1) {
                    return dis;
                }
                for (int k = 0; k < dirLen; k++) {
                    int line = x + dir[k][0];
                    int column = y + dir[k][1];
                    if (line >= 0 && line < len &&
                            column >= 0 && column < len &&
                            grid[line][column] == 0) {
                        int newCode = line * len + column;
                        queue.offer(newCode);
                        grid[line][column] = 1;
                    }
                }
            }

        }
        return -1;
    }
    static int xLen,yLen;
    public static void solve() {
        char[][] board ={
                {'X','O','X','O','X','O'},
                {'O','X','X','X','O','X'},
                {'X','X','X','O','X','O'},
                {'O','X','O','X','O','X'}
        };
        xLen = board.length;
        yLen = board[0].length;

        /**
         * 0 1 2 3 4 5
           1         4
         * 2         3
         * 3         2
         * 4         1
         * 5 4 3 2 1 0
         */
        //两个循环把四周的标记A，因为四周不能被包围
        for(int i=0;i<xLen;i++){
            solveDef(board , i,0);
            solveDef(board , i,yLen-1);
        }
        for(int i=0;i<yLen;i++){
            solveDef(board , 0,i);
            solveDef(board , xLen-1,i);
        }
        //把四周的A还原成O, 把中间的O改为X
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public static void solveDef(char[][] board,int x,int y){
        if(x < 0 || x >=xLen || y < 0 || y >=yLen || board[x][y]!= 'O' ){
            return;
        }
        board[x][y] = 'A';
        solveDef(board,x+1,y);
        solveDef(board,x-1,y);
        solveDef(board,x,y+1);
        solveDef(board,x,y-1);
    }

    static List<List<Integer>> ans = new ArrayList<List<Integer>>();
    static Deque<Integer> stack = new ArrayDeque<Integer>();
    static boolean[] vis ;
    public static List<List<Integer>> allPathsSourceTarget() {
        int[][] graph =  {{1,2},{3},{3},{}};
        stack.offerLast(0);
        dfs(graph, 0, graph.length - 1);
        return ans;
    }

    public static List<List<Integer>> subsets( ) {
          int[] nums = {1,2,3};
        subs2(nums,0,nums.length);
        return ans;
    }
    public static void subs2(int [] nums,int k,int len){
        ans.add(new ArrayList<>(stack));
        for(int i=k;i<len;i++){
            stack.offer(nums[i]);
            subs2(nums,i+1,len);
            stack.removeLast();
        }
    }
    public static void subs(int [] nums,int len,boolean [] vis){
       if(stack.size() >= len){
           ans.add(new ArrayList<>(stack));
           return;
       }
       for(int i=0;i<len;i++){
           if(vis[i]  ){
               continue;
           }

           if(i>0 && nums[i-1] == nums[i] ){
               continue;
           }
           stack.offer(nums[i]);
           vis[i] = true;
           subs(nums,len,vis);
           stack.removeLast();
           vis[i] = false;
       }
    }

    public static List<List<Integer>> permuteUnique() {
        int[] nums = {1,1,2};
        Arrays.sort(nums);
        vis =new boolean[nums.length];
        subs(nums,nums.length,vis);
        return ans;
    }
    static int count = 0;
    public static List<List<Integer>> combinationSum() {
        int[] candidates = {10,1,2,7,6,1,5};
        int len = candidates.length;
        Arrays.sort(candidates);
        //subs2(candidates,candidates.length,0,target);
        return ans;
    }

    public static void subs2(int [] nums,int len,int index,int tag){
        if(tag ==0){
            ans.add(new ArrayList<>(stack));
            return;
        }
        for(int i=index;i<len;i++){
            if ((i > index && nums[i] == nums[i - 1])|| (tag - nums[i] < 0)) continue;
            stack.offer(nums[i]);
            subs2(nums,len,i+1,tag - nums[i]);
            stack.removeLast();
        }
    }
    public static List<String> letterCombinations() {
        String digits ="23";
        substr(digits,0,digits.length(),new StringBuffer());
        return result;
    }
    static List<String> result = new ArrayList<String>();
    public static void substr(String digits,int index,int len,StringBuffer sb){
       /* if(sb.length() == len){
            result.add(sb.toString());
            return;
        }
        String  str = map.get(digits.charAt(index));
        for(int i=0;i<str.length();i++){
            sb.append(str.charAt(i));
            substr(digits,index+1,len,sb);
            // 删除最后一个元素 如：stack.removeLast()
            sb.deleteCharAt(index);
        }*/
    }

    public static List<String> generateParenthesis() {
        int n = 3;
        parenthesis(0,0,n,new StringBuffer());
        return result;
    }
    public static void parenthesis(int open,int close,int n,StringBuffer sb){
        if(sb.length() == n*2){
            result.add(sb.toString());
            return;
        }
        //123 123 ((())) ((
        //121 323(()()) (()
        //121 233(())() (
        //112 323()(()) ()(
        //112 233 ()()()
        if(open < n){
            sb.append('(');
            parenthesis(open+1,close,n,sb);
            sb.deleteCharAt(sb.length()-1);
        }
        if(close <open){
            sb.append(')');
            parenthesis(open,close+1,n,sb);
            sb.deleteCharAt(sb.length()-1);
        }
    }

    public static void dfs(int[][] graph, int x, int n) {
        if (x == n) {
            ans.add(new ArrayList<Integer>(stack));
            return;
        }
        for (int y : graph[x]) {
            stack.offerLast(y);
            dfs(graph, y, n);
            stack.pollLast();
        }
    }
    public static boolean exist() {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "SEE";
        int len = board.length,detLen = board[0].length;
        for(int i=0;i<len;i++){
           for(int j=0;j<detLen;j++){
               if(checkUs(board,word,len,detLen,i,j,0)){
                   return true;
               }
           }
        }
        return false;
    }

    public static boolean checkUs(char[][] board,String subStr,int len,int detLen,int i,int j,int index) {
        if(i<0 || j<0 || i>=len || j>=detLen || board[i][j]=='.' || board[i][j]!=subStr.charAt(index)){
            return false;
        }else if(index == subStr.length()-1){
            return true;
        }else {
            char temp = board[i][j];
            board[i][j] = '.';
            boolean b = checkUs(board,subStr,len,detLen,i+1,j,index+1) ||
                    checkUs(board,subStr,len,detLen,i-1,j,index+1) ||
                    checkUs(board,subStr,len,detLen,i,j+1,index+1) ||
                    checkUs(board,subStr,len,detLen,i,j-1,index+1);
            board[i][j] = temp;
            return b;
        }
    }

    public static int rob() {
        int nums[] = {2,3,1,1,4};
        int len = nums.length,count = 0,res=0,end=0;
        for(int i=0;i<len-1;i++){
            count = Math.max(i + nums[i] , count);
            if(end ==i){
                res++;
                end = count;
            }
        }
        return res;
    }
    public static int uniquePaths() {
        int m=3,n=7;
        int []grid = new int[n];
        Arrays.fill(grid,1);
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                grid[j]+= grid[j-1];
            }
        }
        return grid[n-1];
    }
    public static boolean wordBreak() {
        String s = "catsandog";
        List <String> list = new ArrayList<String>(){{
            add("cats");
            add("g");
            add("sand");
            add("ando");
            add("cat");
        }};
        int len = s.length();
        boolean f [] = new boolean[len+1];
        f[0]=true;
        for(int i=1;i<=len;i++){
            for(int j=0;j<i;j++){
                if(f[j] && list.contains(s.substring(j,i))){
                    f[i]=true;
                    break;
                }
            }
        }
        return f[len];
    }
    public static int numDecodings() {

        String s = "111021";
        int len = s.length();
        int [] f = new int[len+1];
        f[0] = 1;
        for(int i=1;i<=len;i++){
            //因为是i-1 所以是从下标1开始,因为'0'是不可拆分的
            if(s.charAt(i-1)!='0'){
                f[i]+=f[i-1];
            }
            //如果i-2不是0 并且，下标（i-1 + i-2）*10 小于26，则当前继续加f[i-2]
            if(i >1 && s.charAt(i-2) !='0' &&
                    (s.charAt(i-2)-'0') * 10 + (s.charAt(i-1)-'0')<=26){
                f[i]+=f[i-2];
            }
        }
        return f[len];
    }
    public static int numberOfArithmeticSlices() {
        int[] nums = {1,2,3,5,4};
        int len = nums.length;
        if(len < 2){
            return 0;
        }
        int sum= nums[0] -nums[1],t=0,count=0;
        for(int i=2;i<len;i++){
            if(sum == nums[i-1] - nums[i]){
               t++;
            }else {
                sum = nums[i-1] - nums[i];
                t=0;
            }
            count += t;
        }
        return count;
    }
    public static int lengthOfLIS() {
        int[] nums = {10,9,2,5,3,7,101,18};
        int len = nums.length;
        //记录当前最大长度
        int t[] = new int[len];
        t[0] = 1;
        int max=1;
        for(int i=1;i<len;i++){
            t[i] = 1;
            for(int j=0;j<i;j++){
                //符合增长序列，则获取当前最大长度
                if(nums[i] > nums[j]){
                    t[i] = Math.max(t[i],t[j]+1);
                }
            }
            //获取每次最大长度，小则替换
            max = Math.max(max,t[i]);
        }
        return max;
    }
    public static void main(String[] args) {
        List list =null;
        Map<String,List> listMap = new HashMap<>();
        listMap.put("111",list);
        list = new ArrayList();


        //String s = "111";
        Option option = (x)-> x+"1111";
        System.out.println(option.cacl("222"));
        sort2(new int[]{5,4,3,2},0,3);
        //System.out.println(grid[n-1]);

        //generateParenthesis();
        String s = longestPalindrome(  "cbba");

       /* ListNode l5 = new ListNode(4,null);
        ListNode l4 = new ListNode(3,l5);*/
        ListNode l3 = new ListNode(2,null);
        ListNode l2 = new ListNode(1,l3);
        ListNode l1 = new ListNode(1,l2);
        deleteDuplicates();
        /*int [] [] firstList = {{0,2},{5,10},{13,23},{24,25}}, secondList = {{1,5},{8,12},{15,24},{25,26}};
          int result [][] =  intervalIntersection(firstList,secondList);*/
        int [][] isConnected = {{1,0,0},{0,1,0},{0,0,0}};
    }
    public static int findNumberOfLIS() {
        int[] nums = {2,2,2,2,2,2};
        int len = nums.length,maxLen=0,ans = 0;
        int f [] =new int[len];//记录当前最大长度
        int n [] =new int[len];//统计当前最大长度数量
        for(int i=0;i<len;i++){
            f[i] = 1;
            n[i] = 1;
            for(int j=0; j<i; j++){
                //如果nums[i]大于nums[j] 符合递增子序列
                if (nums[i] > nums[j]) {
                    //f[j]+1大于当前最大长度，则最大长度+1，重置最大长度数量
                    if(f[j]+1 > f[i]){
                        f[i] = f[j]+1;
                        n[i] = n[j] ;
                    }else if(f[j]+1 == f[i]){ //如果当前最大长度+1等于 当前记录的最大长度。
                        // 则最大长度数量+1
                        n[i]+=n[j];
                    }
                }
            }
            //当前最大长度大于maxLen ，则maxLen等于 当前长度，重置 最大长度数量
            if(f[i] > maxLen){
                maxLen = f[i];
                ans = n[i] ;
            }else if(f[i] ==maxLen){
                //当前最大长度等于maxLen,则 最大长度数量+1
              ans += n[i];
            }
        }
        return ans;
    }
    public static int longestCommonSubsequence(String text1, String text2){
        int m = text1.length(),n = text2.length();
        int [][] f = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            char c = text1.charAt(i-1);
            for(int j=1;j<=n;j++){
                char c2 = text2.charAt(j-1);
                if(c == c2){
                    f[i][j] = f[i-1][j-1] +1;
                }else {
                    f[i][j] = Math.max(f[i-1][j],f[i][j-1]);
                }
            }
        }
        return f[m][n];
    }
    public static int minDistance() {
        String word1 ="intention", word2 ="execution";
        int m = word1.length(),n=word2.length();
        int [][] f = new int[m+1][n+1];
        for (int i = 0; i <=m; i++) {
            f[i][0] = i;
        }
        for (int j = 0; j <=n; j++) {
            f[0][j] = j;
        }
        for(int i=1;i<=m;i++){
            char c1 = word1.charAt(i-1);
            for(int j=1;j<=n;j++){
                char c2 = word2.charAt(j-1);
                int del= f[i-1][j]+1;//word1 删除操作
                int ins = f[i][j-1]+1;//word1插入操作
                int replace = f[i-1][j-1];//word 替换操作
                if(c1!= c2){
                    replace+=1;
                }
                //获取最小操作数
                f[i][j] = Math.min(del,Math.min(ins,replace));

            }
        }
        return f[m][n];
    }
    public static boolean isHappy(){
        int n = 19;
        int curr = n;
        int next = getNext(n);
        while (next != 1 && curr != next) {
            curr = getNext(curr);
            next = getNext(getNext(next));
        }
        return next == 1 ? true : false;
    }

    public static String reverseWords() {
        String s= "a good   example";
        int len = s.length();
        StringBuffer sb = new StringBuffer();
        for(int i=len-1;i>=0;i--){
            int j = 0;
            while (i>=0 && s.charAt(i)!=' '){
              j++;
              i--;
            }
            if(j> 0 ){
                for(int k = i+1; k<= i+j; k++){
                    sb.append(s.charAt(k));
                }
                sb.append(' ');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int maxProduct() {
        int[] nums = {2,3,-2,4};
        int result = nums[0], max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //int mx = max, mn = min;
            max = Math.max(max * nums[i], Math.max(nums[i], min * nums[i]));
            min = Math.min(min * nums[i], Math.min(nums[i], max * nums[i]));
            result = Math.max(max, result);
        }
        return result;
    }
    public static int getNext(int n){
        int sum = 0;
        while (n > 0) {
            int curr = n % 10;
            n = n / 10;
            sum += curr * curr;
        }
        return sum;
    }

    public static int evalRPN() {
        String[] tokens = {"4","13","5","/","+"};
        Deque<Integer> queue = new LinkedList<Integer>();
        for(int i=0;i<tokens.length;i++){
             if(isNumber(tokens[i])){
                queue.push(Integer.parseInt(tokens[i]));
             }else {
                 int n2 = queue.pop();
                 int n1 = queue.pop();
                 switch (tokens[i]){
                     case "+":
                         queue.push(n1+n2);
                         break;
                     case "-":
                         queue.push(n1-n2);
                         break;
                     case "*":
                         queue.push(n1*n2);
                         break;
                     case "/":
                         queue.push(n1/n2);
                         break;
                 }
             }
        }
        return queue.poll();
    }
    public static boolean isNumber(String token){
       return  !("+".equals(token) || "-".equals(token) ||
               "*".equals(token) || "/".equals(token));
    }
    public static int maxPoints() {
        int[][] points = {{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};

        int max =0,len = points.length;
        for(int i=0;i<len;i++){
            if (max >= len - i || max > len / 2) {
                break;
            }
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for(int j=i+1;j<len;j++){
                int one = points[i][0] - points[j][0] ,tow = points[i][1] - points[j][1];
                int gcdXY = gcd( one, tow);
                one /= gcdXY;
                tow /= gcdXY;
                int key = one+tow*20001;
                int value = map.getOrDefault(key,1)+1;
                map.put(key,value);
                max = Math.max(max,value);
            }

        }
        return max;
    }
    public static int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }
    public static int rangeBitwiseAnd() {
        int left =3,right = 7;
        while (left < right){
            right = right & (right-1);
        }
        return right;
    }
    public static int integerBreak() {
        int n = 6;
        if (n < 4) {
            return n - 1;
        }

        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = Math.max(Math.max(2 * (i - 2), 2 * dp[i - 2]),
                    Math.max(3 * (i - 3), 3 * dp[i - 3]));
        }
        return dp[n];
    }

    private static int coinChange(){
        int []coins = {2};
        int amount = 4;
        int len = amount+1;
        int[] dp = new int[len];

        Arrays.fill(dp, len);
        dp[0] = 0;
        for (int coin : coins) {
            // 正序,j从coin开始即可
            for (int j = coin; j <= amount; j++) {
                count++;
                // 前面dp值在有计算过的基础上才能转移
                if(dp[j - coin] != len)
                    dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }
        // 有转移直接返回,没有转移说明凑不成返回-1
        return dp[amount] > amount ? -1 : dp[amount];
       /* int f[] = new int[amount+1];
        int len = f.length;
        Arrays.fill(f,len);
        f[0]=0;
        for(int i=1;i<len;i++){
            for(int j=0;j<nums.length;j++){
                count++;
                if(nums[j] > i){
                    continue;
                }
                f[i] = Math.min(f[i],f[i-nums[j]]+1);
            }
        }
        return f[amount] > amount ? -1 : f[amount];*/
    }
    //m - lcs + n - lcs;
    // 4 - 2 +3 -2=3  4 + 3 -2*2
    public static String breakPalindrome() {
        String palindrome = "abccba";
        int len = palindrome.length(),right = len-1;
        if(len <=1){
            return "";
        }
        char[] ch = palindrome.toCharArray();
        for(int i=0;i<len;i++){
            char temp =ch[i];
            ch[i] = 'a';
            if(checkPalindrome(ch,right)){
                return new String(ch);
            }
            ch[i] =temp;
        }
        return palindrome.substring(0, len - 1) + "b"; //将最后一个改为b

    }

    public static boolean checkPalindrome(char [] ch,int right){
        int left = 0;
        while (left < right){
            if(ch[left] != ch[right]){
                return true;
            }
            left++;
            right--;
        }
        return false;
    }

    public static int longestDecomposition() {
        String text="ghiabcdefhelloadamhelloabcdefghi";
        int len = text.length(),l = 0, r = len,i=0,j = len-1,result=0;
        while(i<j){
            String t1 = text.substring(l,i+1),t2 = text.substring(j,r);
            if(t1.equals(t2)){
                l=i+1;
                r = j;
                result+=2;
            }
            i++;
            j--;
        }
        return result +(l==r?0:1);
    }
    public static int maxArea(int[] height) {
        int len = height.length,left = 0,right = len-1;
        int result = 0;
        while (left < right){
            int index = right - left;
            if(height[left] < height[right]){
                result =   Math.max(result,height[left]*index);
                left++;
            }else {
                result =  Math.max(result,height[right]*index);
                right--;
            }
        }
        return result;
    }
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> list = new ArrayList<>();
        int len = firstList.length,len2 = secondList.length ,i =0, j=0;
        while (i < len && j < len2){
            int left = Math.max(firstList[i][0],secondList[j][0]);
            int right = Math.min(firstList[i][1],secondList[j][1]);
            if(left <= right){
                list.add(new int[]{left,right});
            }
            if(firstList[i][1] > secondList[j][1]){
                j++;
            }else {
                i++;
            }
        }
        return list.toArray(new int[list.size()][]);
    }
    public static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> lists = new ArrayList<>();
        int len = nums.length;
        sort2(nums,0,len-1);
        for(int i=0;i<len;i++){
            int curr = nums[i];
            if(curr> 0){
                break;
            }
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int l=i+1,r=len-1;
            while (l < r){
                int sum = curr + nums[l] +nums[r];
                if(sum == 0){
                    int finalL = l;
                    int finalR = r;
                    List<Integer> list = new ArrayList(){{
                        add(curr);
                        add( nums[finalL]);
                        add( nums[finalR]);
                    }};
                    lists.add(list);
                    while (l< r && nums[l] == nums[l+1]){
                        l++;
                    }
                    while (l< r && nums[r] == nums[r-1]){
                        r--;
                    }
                    l++;
                    r--;
                }else if(sum > 0){
                    r--;
                }else {
                    l++;
                }
            }
        }
        return lists;
    }
    public static int numSubarrayProductLessThanK() {
        int[] nums = {100,101,102}; int k = 100;
        int res = 0,prod  = 1,i=0;
        for(int j=0;j<nums.length;j++){
            prod  *= nums[j];
            while (i <= j && prod  >= k){
                prod  /= nums[i];
                i++;
            }
            res += j - i + 1;
        }
        return res;
    }

    public static int numIslands(char[][] grid) {
        int count = 0,len =grid.length,detLen = grid[0].length;
        for(int i=0;i<len;i++){
            for(int j=0;j<detLen;j++){
                if(grid[i][j]=='1'){
                    def2(i,j,grid,len,detLen);
                    count++;
                }
            }
        }
        return count;
    }

    public static void def2(int i,int j,char[][] grid,int len,int detLen) {
        if(i < 0 || i>=len || j<0 || j>=detLen || grid[i][j]=='0'){
            return;
        }
        grid[i][j] = '0';
        def2(i-1,j,grid,len,detLen);
        def2(i+1,j,grid,len,detLen);
        def2(i,j-1,grid,len,detLen);
        def2(i,j+1,grid,len,detLen);
    }

    public static boolean backspaceCompare(String s, String t) {
        if(s==null && t==null){
            return true;
        }
        if(s == null){
            return false;
        }
        if(t==null){
            return false;
        }
        if(s.equals(t)){
            return true;
        }
        String s2= compare(s);
        String t2= compare(t);
        return s2.equals(t2);
    }

    public static String compare(String s) {
        StringBuffer sb = new StringBuffer();
        int len = s.length();
        for(int i=0;i<len;i++){
            if( s.charAt(i)=='#'){
                if(sb.length() > 0){
                    sb.deleteCharAt(sb.length()-1);
                }
            }else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
    public static ListNode deleteDuplicates() {
        ListNode l5 = new ListNode(1,null);
        ListNode l4 = new ListNode(1,l5);
        ListNode l3 = new ListNode(1,l4);
        ListNode l2 = new ListNode(1,l3);
        ListNode l1 = new ListNode(1,l2);
        ListNode node = new ListNode(99999,l1);
        ListNode node2 = node;
        while(node2.next != null){
            ListNode curr = node2.next;
            if (curr!=null&& curr.val == node2.val){
                node2.next = curr.next;
            }else {
                node2 = node2.next;
            }
            /*if(curr != null && curr.val == node2.val){

            }else {

            }
            if(next != null && curr.val == next.val){
                last = next.val;
                node2.next= next.next;
            }else if(last !=null && last == curr.val){
                node2.next = node2.next.next;
            }else {
                node2 = node2.next;
            }*/
        }
        return  node.next;
    }

    public static void letterCombinations(String digits,int len,int index,StringBuffer sb,List<String> list){
       /* if(index == len){
            list.add(sb.toString());
        }else {
          char key= digits.charAt(index);
          char [] ch = map.get(key).toCharArray();
          for(int i=0;i<ch.length;i++){
              sb.append(ch[i]);
              letterCombinations(digits,len,index+1,sb,list);
              sb.deleteCharAt(index);
          }
        }*/
    }

    public static String intToRoman(int num) {
        StringBuffer sb = new StringBuffer();
        sb.append(thousands[num/1000]);
        sb.append(hundreds[num%1000/100]);
        sb.append(tens[num%100/10]);
        sb.append(ones[num%10/1]);
        return sb.toString();
    }
    public static String longestCommonPrefix(String[] strs){
        String prefix = strs[0];
        for(int i=1;i<strs.length;i++){
            prefix = longestCommonPrefix(prefix,strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }
    public static String longestCommonPrefix(String s1,String s2){
        int len = Math.min(s1.length(),s2.length());
        int index = 0;
        while (index < len && s1.charAt(index) == s2.charAt(index)){
            index++;
        }
        return s1.substring(0,index);
    }
    public static String longestPalindrome(String s){
        if (s == null || s.length() <= 1) {
            return s;
        }
        int start= 0,end = 0 ,max=0;
        for(int i=0;i<s.length();i++){
          /*  int []nums1 =expandAroundCenter(s,i,i);
            int [] nums2 = expandAroundCenter(s,i,i+1);
            int sum = nums1[1] -nums1[0];
            int nums3 [] ={};


            if(nums[0] < nums[1] && sum > max){
                max = sum;
                start = nums[0];
                end=nums[1];*/

        }

        return s.substring(start,end+1);
    }


    public static int[] expandAroundCenter(String s,int left,int right){
        //level cbbd
       while (left>=0 && right<s.length() && s.charAt(left) == s.charAt(right)){
           left--;
           right++;
       }
       return new int[]{left+1,right-1};
    }
    /*public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    public static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }*/
    public static void merge(int[] nums1, int[] nums2, int[] result, int left, int right, int next) {
        if (next >= result.length) {
            return;
        }
        if(left == nums1.length){
            result[next] = nums2[right];
            right++;
        }else if(right == nums2.length){
            result[next] = nums1[left];
            left++;
        } else if (nums1[left] <= nums2[right]) {
            result[next] = nums1[left];
            left++;
        }else if (nums1[left] >= nums2[right]) {
            result[next] = nums2[right];
            right++;
        }
        next++;
        merge(nums1, nums2, result, left, right, next);
    }
    public static int[] twoSum() {
        int [] nums = {2,7,11,15};
        int tag = 18;
        int len =nums.length, left=0,right=len-1;
            while (left < right){
                int sum = nums[left] + nums[right];
            if(sum == tag){
                return new int[]{left,right};
            }
            if(sum<tag){
                left++;
            }else if(sum>tag){
                right--;
            }
        }
        return null;
    }
    public static void letterCasePermutation( char []ch,int index ,List<String> list){
       while (index < ch.length && !Character.isLetter(ch[index])){
           index++;
       }
       if(index >= ch.length){
           list.add(new String(ch));
           return;
       }
        ch[index] ^= 32;
        letterCasePermutation(ch,index+1,list);

        ch[index] ^= 32;
        letterCasePermutation(ch,index+1,list);

    }
    private static void permute(int []  nums,boolean [] sign, int len,Deque<Integer> path,List<List<Integer>> lists){
        if(path.size() == len){
            lists.add(new ArrayList<>(path));
            return;
        }
        for(int i=0;i<len;i++){
            if(!sign[i]){
                path.add(nums[i]);
                sign[i] = true;
                permute(nums,sign,len,path,lists);
                sign[i] = false;
                path.removeLast();
            }
        }
    }
    private static void center( int n,int k,int start,Deque<Integer> path, List<List<Integer>> lists){

        if (n + path.size() +1  - start < k) {
            return;
        }
        if(path.size() == k){
            lists.add(new ArrayList<>(path));
            return ;
        }
        path.add(start);
        start++;
        center(n,k,start,path,lists);
        path.removeLast();
        center(n,k,start,path,lists);

    }



    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 ==null){
            return list2;
        }
        if(list2==null){
            return list1;
        }
        if( list1.val <= list2.val ){
            list1.next = mergeTwoLists(list1.next,list2);
            return list1;
        }else{
            list2.next = mergeTwoLists(list1,list2.next);
            return list2;
        }
    }
    protected static TreeNode merge( TreeNode treeNode1, TreeNode treeNode2){
       /* TreeNode treeNode5 = new TreeNode(5,null,null);
        TreeNode treeNode3 = new TreeNode(3,treeNode5,null);
        TreeNode treeNode2 = new TreeNode(2,null,null);
        TreeNode treeNode1 = new TreeNode(1,treeNode3,treeNode2);

        TreeNode treeNode24 = new TreeNode(4,null,null);
        TreeNode treeNode27 = new TreeNode(7,null,null);
        TreeNode treeNode21 = new TreeNode(1,null,treeNode24);
        TreeNode treeNode23 = new TreeNode(3,null,treeNode27);
        TreeNode treeNode22 = new TreeNode(2,treeNode21,treeNode23);

        TreeNode tt=merge(treeNode1,treeNode22);
        System.out.println(tt);*/
        if(treeNode1==null && treeNode2==null){
            return null;
        }
        if(treeNode1==null){
            return treeNode2;
        }
        if(treeNode2 ==null){
            return treeNode1;
        }
        treeNode1.val = treeNode1.val + treeNode2.val;
        if (treeNode1.left == null && treeNode2.left !=null){
            treeNode1.left = treeNode2.left;
            treeNode2.left = null;
        }
        if(treeNode1.right == null && treeNode2.right !=null){
            treeNode1.right = treeNode2.right;
            treeNode2.right = null;
        }
        merge(treeNode1.left,treeNode2.left);
        merge(treeNode1.right,treeNode2.right);
        return treeNode1;
    }
    public static int maxAreaOfIsland(int[][] grid) {
        /**
         * int [][] grid ={ {0,0,1,0,0,0,0,1,0,0,0,0,0 }, {0,0,0,0,0,0,0,1,1,1,0,0,0 }, {0,1,1,0,1,0,0,0,0,0,0,0,0 }, {0,1,0,0,1,1,0,0,1,0,1,0,0 }, {0,1,0,0,1,1,0,0,1,1,1,0,0 }, {0,0,0,0,0,0,0,0,0,0,1,0,0 }, {0,0,0,0,0,0,0,1,1,1,0,0,0 }, {0,0,0,0,0,0,0,1,1,0,0,0,0 } };
         * int i = maxAreaOfIsland(grid);
         */
        int result = 0, len = grid.length, detlen = grid[0].length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < detlen; j++) {
                if (grid[i][j] == 1) {
                    result = Math.max(result, def(i, j, len, detlen, grid));
                }
            }
        }
        return result;
    }

    public static int def(int i, int j, int len, int detlen, int[][] grid) {
        if (i < 0 || i >= len || j < 0 || j >= detlen || grid[i][j] == 0) {
            return 0;
        }
        int nums = 1;
        grid[i][j] = 0;
        nums += def(i - 1, j, len, detlen, grid);
        nums += def(i + 1, j, len, detlen, grid);
        nums += def(i, j - 1, len, detlen, grid);
        nums += def(i, j + 1, len, detlen, grid);
        return nums;
    }

    public static List<Integer> findAnagrams() {
        String s = "cbaebabacd", p = "abc";
        List<Integer> list = new ArrayList<>();
        int sLen = s.length(),pLen = p.length();
        if (sLen < pLen) {
            return list;
        }
        int [] st = new int[26];
        int [] pt = new int[26];
        char [] sChar = s.toCharArray();
        char [] pChar = p.toCharArray();
        int indexs [] = new int[pLen];
        for(int i=0;i<pLen;i++){
            indexs[i] = pChar[i]-'a';
            ++st[sChar[i]-'a'];
            ++pt[pChar[i]-'a'];
        }
        if(compare(st,pt,indexs)){
            list.add(0);
        }
        for(int j=pLen; j< sLen;j++){
            --st[sChar[j-pLen]-'a'];
            ++st[sChar[j]-'a'];
            if(compare(st,pt,indexs)){
                list.add(j-pLen+1);
            }
        }
        return list;
    }

    private static boolean compare(int [] tag,int []sub,int []indexs){
        for(int index :indexs){
            if(tag[index] != sub[index]){
                return false;
            }
        }
        return true;
    }

    //左右上下
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public  static boolean checkInclusion(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        if (n > m) {
            return false;
        }
        int[] cnt = new int[26];
        for(int i=0;i<n;i++){
            --cnt[s1.charAt(i)-'a'];
        }
        int last=0;
        for(int k=0;k<m;k++){
            int index = s2.charAt(k)-'a';
            ++cnt[index];
            while (cnt[index] > 0){
                --cnt[s2.charAt(last) - 'a'];
                last++;
            }
            if(k - last + 1 ==n){
                return true;
            }
        }
        return false;
    }



    /**
     * 双指针排序
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void sort2(int[] arr, int left, int right) {
        if(left >= right){
            return;
        }
        int base = arr[right];
        int k = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < base) {
                if (k != i) {
                    int temp = arr[i];
                    arr[i] = arr[k];
                    arr[k] = temp;
                }
                k++;//统计小于基准值的数量
            }
        }
        //小于基准值的数量之和 ，和最后一个进行调换位置
        // 例如 3，2，6，5，4 基准值是4 k统计比4小的数量是2，right是4
        // 所有下标2和下标4进行互换位置  3，2，4，5，6
        int temp = arr[k];
        arr[k] = arr[right];
        arr[right] = temp;
        //K 的位置已经判断过了，所以终止位置是k-1 然后左边和左边比较
        sort2(arr, left, k - 1);
        //K 的位置已经判断过了 所以是k+1右边和右边比较
        sort2(arr, k + 1, right);

    }


    public static void sort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int base = nums[left];
        //终止条件 i >= j
        while (i < j) {

            while (i < j && nums[j] >= base) {// 找到小于基准值的下标
                j--;
            }

            nums[i] = nums[j];

            while (i < j && nums[i] <= base) {//找到小于基数的下标
                i++;

            }
            nums[j] = nums[i];

        }
        nums[i] = base;
        sort(nums, left, i);
        sort(nums, i + 1, right);
    }


    /**
     * 异或判断重复值
     */
    public static void test() {
        int nums[] = new int[]{1, 3, 1, 3, 2};
        int ans = nums[0];
        if (nums.length > 1) {
            for (int i = 1; i < nums.length; i++) {
                ans = ans ^ nums[i];
            }
        }
        System.out.println(ans);
    }

    /**
     * 返回二进制数据有多少个1
     *
     * @param n
     * @return
     */
    private static int Bit(int n) {
        n = (n & M1) + ((n >> 1) & M1);
        n = (n & M2) + ((n >> 2) & M2);
        n = (n & M4) + ((n >> 4) & M4);
        n = (n & M8) + ((n >> 8) & M8);
        n = (n & M16) + ((n >> 16) & M16);
        return n;
    }

    /**
     * 二进制转换
     *
     * @param n
     * @return
     */
    private static int convertBinary(int n) {
        n = n >> 1 & M1 | ((n & M1) << 1);
        n = n >> 2 & M2 | ((n & M2) << 2);
        n = n >> 4 & M4 | ((n & M4) << 4);
        n = n >> 8 & M8 | ((n & M8) << 8);
        n = n >> 16 & M16 | ((n & M16) << 16);
        return n;
    }

    public static int minimumTotal() {

        List<List<Integer>> triangle = new ArrayList<>();
        List<Integer> list1 = new ArrayList();
        list1.add(2);

        List<Integer> list2 = new ArrayList();
        list2.add(3);
        list2.add(4);

        List<Integer> list3 = new ArrayList();
        list3.add(6);
        list3.add(5);
        list3.add(2);
        List<Integer> list4 = new ArrayList();
        list4.add(4);
        list4.add(5);
        list4.add(6);
        list4.add(3);
        triangle.add(list1);
        triangle.add(list2);
        triangle.add(list3);
        triangle.add(list4);
        //[2],[3,4],[6,5,2],[4,5,6,3]
        // 5 6
        //11 10 8
        // 15 15 14  11
        int size = triangle.size();
        int[][] nums = new int[size][size];
        nums[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < size; i++) {
            nums[i][0] = nums[i-1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                nums[i][j] = Math.min(nums[i-1][j-1],nums[i-1][j])  + triangle.get(i).get(j);
            }
            nums[i][i] = nums[i-1][i-1] + triangle.get(i).get(i);
        }
        //System.out.println(nums);
        return 0;
    }

    private static void dynamic() {

        int nums[] = new int[]{2, 7, 9, 3, 1};
        int prev = nums[0];
        int curr = nums[1];
        for (int i = 2; i < nums.length; i++) {
            int temp = Math.max(curr, prev + nums[i]);
            prev = curr;
            curr = temp;
        }
        System.out.println(curr);
    }





}
 class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
 class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}
@FunctionalInterface
interface Option{
    String cacl(String fuhao);
}









