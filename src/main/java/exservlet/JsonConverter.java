//package exservlet;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Arrays;
//
//public class JsonConverter {
//
//    public static void main(String[] args) throws Exception {
//
//        Post post = new Post();
//        post.setId(1L);
//        post.setTitle("Covert Json");
//        post.setHidden(false);
//        post.setTags(Arrays.asList("java", "json"));
//
//        // a) Object -> Json
//         String json = new ObjectMapper().writeValueAsString(post);
//
//      //  System.out.println(json);
//
//
//        // b) Json -> Object
//         Post post1 = new ObjectMapper().readValue(json,Post.class);
//
//        System.out.println(post1);
//
//    }
//
//
//}
