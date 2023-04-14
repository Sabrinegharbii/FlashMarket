package com.example.marketplace.services;
import com.example.marketplace.entities.*;
import com.example.marketplace.repository.IPostRepo;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.repository.ImageRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j

public class PostServ implements IPostServ {
    final IPostRepo iPostRepo;
    final IUserRepository iUserRepository;
    final MailerService mailerService;
    final CloudinaryService cloudImage;
    final ImageRepo mediaRepo;

    @Override
    public List<Post> getAllPost() {
        List<Post> p = new ArrayList<>();
        iPostRepo.findAll().forEach(p::add);
        return p;
    }

    @Override
    public Post updatePost(Post post) {
        User u = iUserRepository.findById(post.getUser().getId()).orElse(null);
        LocalDateTime time1 = LocalDateTime.now();
        BadWordImpl b = new BadWordImpl();
        if (b.filterText(post.getBody()) || b.filterText(post.getTitle())) {
            if (u.getBan() == null) {
                u.setBan(0);
            }
            if (u.getBan() == 0) {
                System.out.println("warning of bad words");
            }
            if (u.getBan() == 1) {
                System.out.println("you have banned for 1h");
                u.setBanTime(time1.plusHours(1));
            }
            if (u.getBan() == 2) {
                System.out.println("you have banned for 24h");
                u.setBanTime(time1.plusDays(1));
            }
            if (u.getBan() == 3) {
                System.out.println("you have banned for 1 month");
                u.setBanTime(time1.plusMonths(1));
            }
            if (u.getBan() == 4) {
                System.out.println("you have banned for 100 years");
                u.setBanTime(time1.plusYears(100));
            }

            u.setBan(u.getBan()+1);
            iUserRepository.save(u);
            return null;
        }
        post.setUser(u);
        return iPostRepo.save(post);
    }
    @Override
    public Post addPost(Post c) {
        return iPostRepo.save(c);
    }

    @Override
    public Post getPost(Integer idC) {
        return iPostRepo.findById(idC).orElse(null);
    }

    @Override
    public void removePost(Integer idC) {
        Post p=iPostRepo.findById(idC).orElse(null);
        p.setUser(null);
        iPostRepo.deleteById(idC);

    }

    @Override
    public List<Comment> getAllCommentOfPost(Integer id) {
        return iPostRepo.getAllcomment(id);
    }


    @Override
    public String addAndAssignPostToPostUser(Post post, Integer id) {
        User u = iUserRepository.findById(id).orElse(null);
        String message="";
        LocalDateTime time1 = LocalDateTime.now();
        BadWordImpl b = new BadWordImpl();
        if (b.filterText(post.getBody())||b.filterText(post.getTitle())) {
            if(u.getBan()==null){
                u.setBan(0);
            }
            if(u.getBan()==0){
                message="warning of bad words";
            }
            if(u.getBan()==1){
                message="you have banned for 1h";
                u.setBanTime(time1.plusHours(1));
            }
            if(u.getBan()==2) {
                message="you have banned for 24h";
                u.setBanTime(time1.plusDays(1));
            }
            if(u.getBan()==3) {
                message="you have banned for 1 month";
                u.setBanTime(time1.plusMonths(1));
            }
            if(u.getBan()==4) {
                message="you have banned for 100 years";
                u.setBanTime(time1.plusYears(100));
            }

            u.setBan(u.getBan()+1);
            iUserRepository.save(u);
            return message;
        }
        post.setUser(u);
        iPostRepo.save(post);
        return "Post is added successfully!" ;
    }
    @Override
    public int nbPostLikeTotal(Integer id) {
        return iPostRepo.nbPostLikeTotal(id);
    }
    @Override
    public void affecterSignal(Integer idP, Integer idU) {
        User u = iUserRepository.findById(idU).orElse(null);
        Post p = iPostRepo.findById(idP).orElse(null);
        p.getReported().add(u);
        iPostRepo.save(p);
    }

    @Override
    public int NbSignale(Integer id) {
        int x = 0;
        Post p = iPostRepo.findById(id).get();
        for (int i = 0; i < p.getReported().size(); i++) {
            x++;
        }
        return x;
    }
   //@Scheduled(cron = "*/10 * * * * *")
    @Override
    public String ArchiverAutomatique() {
        for (Post post : iPostRepo.findAll()) {
            if (post.getReported().size() > 0) {
                post.setArchiver(true);
                iPostRepo.save(post);
                mailerService.sendEmail(post.getUser().getEmail(), "Post archived", "Hello " +post.getUser().getFirstName() + " " + post.getUser().getLastName() +" Your Post  "+post.getTitle()+" is archived");
                return post.getUser().getFirstName()+" your post is archived";
            }
        }
        return null;
    }


  //  @Scheduled(cron = "*/10 * * * * *")
    @Override
    public Post bestPost() {
        int x = 0;
        int totalFinal=0;

        for (Post post : iPostRepo.findAll()) {
            int total=0;
            if(post.isArchiver()==false){
                if(post.getDateCre().plusDays(7).isAfter(LocalDateTime.now())){
                total=total+(iPostRepo.nbLovePost(post.getIdPost()))*2+(iPostRepo.nbLIKEPost(post.getIdPost()))-(iPostRepo.nbDISLIKEPost(post.getIdPost()))-(iPostRepo.nbDISLIKEPost(post.getIdPost()))*2;
                if (total>totalFinal){
                    totalFinal=total;
                    x = post.getIdPost();
                }
            }}
        }
        if (x != 0) {
            Post y = iPostRepo.findById(x).orElse(null);
            Date d = new Date();
            mailerService.sendEmail(y.getUser().getEmail(), "Best Post Winner", "Hello " + y.getUser().getFirstName() + " " + y.getUser().getLastName() + "\nYour Post is the best with "+totalFinal +"Point \nFor week " + d + "\n");
            return y;
        }
        return null;
    }
    @Override
    public String Statistique(){

        int user_nbPost=0,user_nbComment=0;
        String user_BestPost="",user_BestComment="",RolePost="",RoleComment="";
        String t;
        for(User u:iUserRepository.findAll()){
            if(iPostRepo.mostUserPost(u.getId())>user_nbPost){
                user_nbPost=iPostRepo.mostUserPost(u.getId());///nb Post
                user_BestPost=u.getFirstName()+" "+u.getLastName();///get nom et prenom
                for(Role r:u.getRoles()){
                    RolePost=r.getName().toString();
                }
            }
            if(iPostRepo.mostUserComment(u.getId())>user_nbComment){
                user_nbComment=iPostRepo.mostUserComment(u.getId());
                user_BestComment=u.getFirstName()+" "+u.getLastName();
                for(Role r:u.getRoles()){
                    RoleComment=r.getName().toString();
                }
            }
        }
        t="User: "+user_BestPost+" with role "+RolePost+" have the most number of posts with "+user_nbPost+"\n"+"User: "+user_BestComment+" with role "+RoleComment+" have the most number of comments with "+user_nbComment+" \n"+"All rect For Post are:"+iPostRepo.React();
        return t;
    }
    



}