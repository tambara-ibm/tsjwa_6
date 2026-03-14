package one.tmbrms.readingsns;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import one.tmbrms.readingsns.entity.*;
import one.tmbrms.readingsns.repository.*;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    //(田村)キーワード検索のためパラメーター受取れるようにした
    public String index(Model model, @RequestParam(name = "q", required = false) String q){

        String keyword = (q == null) ? "" : q.trim();

        //(田村)キーワード有無によりクエリを分岐
        List<Message> messages = keyword.isEmpty()
                ? messageRepository.findAll()
                : messageRepository.findByContentContaining(keyword);

        var articles = messages.stream()
                .map(m -> new Article(m))
                .sorted((a, b) -> Integer.compare(b.message.id, a.message.id))
                .toList();

        model.addAttribute("articles", articles);
        model.addAttribute("query", keyword); 

        return "index";
    }
    
    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String user(Model model, @PathVariable int id){
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("articles", messageRepository.findByUserId(id).stream().map(m -> new Article(m)).toList()); 
        model.addAttribute("alert", "");
        return "user";
    }

    @PostMapping("/post")
    public String post(Model model, @RequestParam int userid,
                       @RequestParam String isbn,
                       @RequestParam String content){

        
        System.out.println(String.format("userid=%s, isbn=%s, content=%s", userid, isbn, content));

        // useridが存在するか確かめる
        User user = userRepository.findById(userid).get();
        
        // isbnがbookテーブルに存在するか確かめる
        var bookOpt = bookRepository.findById(isbn);
        if(!bookOpt.isPresent()){
            model.addAttribute("user", user);
            model.addAttribute("articles", messageRepository.findByUserId(userid).stream().map(m -> new Article(m)).toList()); 
            model.addAttribute("alert", "登録されていない本です");
            return "user";
        }
        
        // Messageエンティティを作成して保存する
        var message = new Message(
            messageRepository.nextId(), user, bookOpt.get(), content, Instant.now().toString());
        messageRepository.save(message);
        
        return "redirect:/";
    }

    // This is greeting sample
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }

    // This is thymeleaf sample
    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model){

        model.addAttribute("first","ファースト");
        model.addAttribute("second","セカンド");
        model.addAttribute("third","サード");

        var books = new ArrayList<Book>();

        var book = new Book("シャーロック★ホームズの凱旋", "9784120057342");
        books.add(book);

        book = new Book("入門 モダンLinux", "9784814400218");
        books.add(book);

        model.addAttribute("books", books);

        return "thymeleaf";
    }
}
