<?php 
  class DbOperation{
    function __construct(){
      require dirname(__FILE__).'/dbConnect.php';

      $db = new dbConnect;
      $this->con = $db->connect();
    }

    public function createUser($name,$username,$email,$phonenum,$password){
      if(!$this->isPhonenumExist($phonenum)){
      if(!$this->isEmailExist($email)){
        if(!$this->isUsernameExist($username)){
        $stmt = $this->con->prepare("insert into userdata (name,username,email,phonenum,password) values (?,?,?,?,?)");
        $stmt->bind_param("sssss",$name,$username,$email,$phonenum,$password);
        if($stmt->execute()){   
          return USER_CREATED;
        }else{
          return USER_FAILURE;
        }
        }else{
        return USER_EXIST;
        }
      }else{
        return EMAIL_EXIST;
      }
      }else{
      return PHONENUM_EXIST;
      }
    }
    
    public function updateUser($name,$username,$email,$phonenum,$userid){
      /*if(!$this->isPhonenumExist($phonenum)){
      if(!$this->isEmailExist($email)){
        if(!$this->isUsernameExist($username)){*/
        $stmt = $this->con->prepare("update userdata set name = ?, username = ?, email = ?, phonenum = ? where userid = ?");
        $stmt->bind_param("ssssi",$name,$username,$email,$phonenum,$userid);
        if($stmt->execute()){   
          return USER_UPDATED;
        }else{
          return USER_FAILURE;
        }
        /*}else{
        return USER_FAILURE;
        }
      }else{
        return USER_FAILURE;
      }
      }else{
      return USER_FAILURE;
      }*/
    }

    public function updateUserImage($id,$filename){
      $stmt = $this->con->prepare("update userdata set imagepath = ? where userid = ?");
      $stmt->bind_param("si",$filename,$id);
      return $stmt->execute();
    }

    public function deleteUserImage($id){
      $stmt = $this->con->prepare("update userdata set imagepath = null where userid = ?");
      $stmt->bind_param("i",$id);
      return $stmt->execute();
    }
  
    public function newReport($userid,$specific,$issues,$comment,$attrid,$uploadedFile){
      $stmt = $this->con->prepare("insert into report (userid,specifics,issue,comment,attrid) values (?,?,?,?,?)");
      $stmt->bind_param("isssi",$userid,$specific,$issues,$comment,$attrid);
      $stmt->execute();
      $stmt->store_result();
      $data = array();
      if($uploadedFile == null){
        $data['status'] = REPORT_CREATED;
      }else{
        $reportId = $this->getLastReportId();
        if ($uploadedFile->getError() === UPLOAD_ERR_OK){
          $extension = pathinfo($uploadedFile->getClientFilename(), PATHINFO_EXTENSION);
  
          $lastIndex = $this->getLastReportImageIndex($attrid);
          $index = $lastIndex +1;
          
          $filename = sprintf('%s.%0.8s', 'report_img-'.$attrid.'-'.$reportId.'-'.$index, $extension);
          
          $directory = __DIR__.'/../public/uploads/report_images';
          $uploadedFile->moveTo($directory . DIRECTORY_SEPARATOR . $filename);
  
          $result = $this->insertReportImage($attrid,$userid,$reportId,$filename);
          
          if($result!=null){
              $url = $filename;
              $data['status'] = REPORT_CREATED;
              $data['url'] = $url;
          }else{
            $data['status'] = REPORT_FAILURE;
          }
        }
      }
      return $data;      
    }
  
    public function newReview($userid,$rating,$review,$attrid,$uploadedFile){
      if($this->isUserReview($userid,$attrid)){
        $stmt = $this->con->prepare("update rate set rating = ?, review = ?, datecreated=now() where userid=? and attrid=?;");
        $stmt->bind_param("isii",$rating,$review,$userid,$attrid);
        $stmt->execute();
        $stmt->store_result();
        $data = array();
        if($uploadedFile == null){
          if($this->isUserReviewImage($userid,$attrid)){
            $res = $this->deleteReviewImage($attrid,$userid);
            if($res!=null){
              $data['status'] = REVIEW_CREATED;
            }else{
              $data['status'] = REVIEW_FAILURE;
            }
          }else{
            $data['status'] = REVIEW_CREATED;
          }
        }else{
          if ($uploadedFile->getError() === UPLOAD_ERR_OK){
            $extension = pathinfo($uploadedFile->getClientFilename(), PATHINFO_EXTENSION);
    
            $lastIndex = $this->getLastReviewImageIndex($attrid);
            $index = $lastIndex +1;

            $filename = sprintf('%s.%0.8s', 'review_img-'.$attrid.'-'.$index, $extension);

            $directory = __DIR__.'/../public/uploads/review_images';
            $uploadedFile->moveTo($directory . DIRECTORY_SEPARATOR . $filename);

            if($this->isUserReviewImage($userid,$attrid)){
              $result = $this->updateReviewImage($attrid,$userid,$filename);
            }else{
              $result = $this->insertReviewImage($attrid,$userid,$filename);
            }

            if($result!=null){
              $url = $filename;
              $data['status'] = REVIEW_CREATED;
              $data['url'] = $url;
            }else{
              $data['status'] = REVIEW_FAILURE;
            }
          }else{
            $data['status'] = REVIEW_FAILURE;
          }
        }
        return $data;

      }else{
        $stmt = $this->con->prepare("insert into rate (userid,rating,review,attrid) values (?,?,?,?)");
        $stmt->bind_param("iisi",$userid,$rating,$review,$attrid);
        $stmt->execute();
        $stmt->store_result();
        $data = array();
        if($uploadedFile == null){
          $data['status'] = REVIEW_CREATED;
        }else{
          if ($uploadedFile->getError() === UPLOAD_ERR_OK){
              $extension = pathinfo($uploadedFile->getClientFilename(), PATHINFO_EXTENSION);
      
              $lastIndex = $this->getLastReviewImageIndex($attrid);
              $index = $lastIndex +1;

              $filename = sprintf('%s.%0.8s', 'review_img-'.$attrid.'-'.$index, $extension);

              $directory = __DIR__.'/../public/uploads/review_images';
              $uploadedFile->moveTo($directory . DIRECTORY_SEPARATOR . $filename);

              $result = $this->insertReviewImage($attrid,$userid,$filename);

              if($result!=null){
                $url = $filename;
                $data['status'] = REVIEW_CREATED;
                $data['url'] = $url;
              }else{
                $data['status'] = REVIEW_FAILURE;
              }
          }else{
            $data['status'] = REVIEW_FAILURE;
          }
        }
        return $data;
      }
    }
    
    public function isUserReview($userid,$attrid){
      $stmt = $this->con->prepare("select review from rate where userid=? and attrid =?");
      $stmt -> bind_param("ii",$userid,$attrid);
      $stmt -> execute();
      $stmt -> store_result();
      return $stmt->num_rows>0;
    }
    
    public function isUserReviewImage($userid,$attrid){
      $stmt = $this->con->prepare("select * from imagereview where userid=? and attrid =?");
      $stmt->bind_param("ii",$userid,$attrid);
      $stmt->execute();
      $stmt->store_result();
      return $stmt->num_rows>0;
    }
  
    public function isUsernameExist($username){
      $stmt = $this->con->prepare("select username from userdata where username=?");
      $stmt->bind_param("s",$username);
      if($stmt->execute()){
      $stmt->store_result();
      return $stmt->num_rows>0;
      }else{
      echo('query error');
      }  
    }
  
    public function isEmailExist($email){
      $stmt = $this->con->prepare("select email from userdata where email=? ");
      $stmt -> bind_param("s",$email);
      $stmt -> execute();
      $stmt -> store_result();
      return $stmt->num_rows>0;
    }

    public function isPhonenumExist($email){
      $stmt = $this->con->prepare("select phonenum from userdata where phonenum=? ");
      $stmt -> bind_param("s",$email);
      $stmt -> execute();
      $stmt -> store_result();
      return $stmt->num_rows>0;
    }
    
    public function userLogin($username, $password){
      if($this->isUsernameExist($username)){
      $hashed_password = $this->getUsersPasswordByUsername($username); 
      if(md5($password) == $hashed_password){
        return USER_AUTHENTICATED;
      }else{
        return USER_PASSWORD_DO_NOT_MATCH; 
      }
      }else{
      return USER_NOT_FOUND; 
      }
    }
   
    private function getUsersPasswordByUsername($username){
      $stmt = $this->con->prepare("SELECT password FROM userdata WHERE username = ?");
      $stmt->bind_param("s", $username);
      $stmt->execute(); 
      $stmt->bind_result($password);
      $stmt->fetch(); 
      return $password; 
    }
  
    public function getUserByUsername($username){
      $stmt = $this->con->prepare("SELECT userid,name,email,phonenum,username FROM userdata WHERE username = ?");
      $stmt->bind_param("s", $username);
      $stmt->execute(); 
      $stmt->bind_result($userid,$name,$email,$phonenum,$username);
      $stmt->fetch();
      $user = array();
      $user['userid']=$userid;
      $user['name']=$name;
      $user['email']=$email;
      $user['phone number']=$phonenum;
      $user['username']=$username;
      return $user;
    }
    
    public function getUserById($userid){
      $stmt = $this->con->prepare("SELECT userid,name,email,phonenum,username,imagepath FROM userdata WHERE userid = ?");
      $stmt->bind_param("i", $userid);
      $stmt->execute(); 
      $stmt->bind_result($userid,$name,$email,$phonenum,$username,$imagepath);
      $stmt->fetch();
      $user = array();
      $user['userid']=$userid;
      $user['name']=$name;
      $user['email']=$email;
      $user['phone number']=$phonenum;
      $user['username']=$username;
      $user['image']=$imagepath;
      
      return $user;
    }
  
    public function loaddata(){
      $stmt = $this->con->prepare("SELECT * FROM touristattraction");
      $stmt->execute();
      
      $stmt->bind_result($attrid,$attrname,$location,$details,$schedule);
      $data=array();
      while($stmt->fetch()){
      $tmp=array();
      $tmp['attrid'] = $attrid;
      $tmp['attrname']=$attrname;
      $tmp['location']=$location;
      $tmp['details']=$details;
      $tmp['schedule']=$schedule;
      array_push($data,$tmp);
      }
      return $data;
    }
  
    public function loadattrreview($attrid){
      $stmt = $this->con->prepare("select userid, username, rating, review, datecreated, attrid from rate natural join userdata where attrid = ?");
      $stmt->bind_param("i", $attrid);
      $stmt->execute();
      
      $stmt->bind_result($userid,$username,$rating,$review,$datecreated,$attrid);
  
      $data=array();
      while($stmt->fetch()){
      $tmp=array();
      $tmp['userid'] = $userid;
      $tmp['username']=$username;
      $tmp['rating']=$rating;
      $tmp['review']=$review;
      $tmp['datecreated']=$datecreated;
      $tmp['attrid']=$attrid;
      array_push($data,$tmp);
      }
      return $data;
    }
  
    public function loadattrreport($attrid){
      $stmt = $this->con->prepare("select userid, username, reportid,specifics, issue, comment, datecreated, status, attrid from report NATURAL JOIN userdata where attrid = ?");
      $stmt->bind_param("i", $attrid);
      $stmt->execute();
      
      $stmt->bind_result($userid,$username,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrid);
  
      $data=array();
      while($stmt->fetch()){
      $tmp=array();
      $tmp['userid'] = $userid;
      $tmp['username']=$username;
      $tmp['reportid']=$reportid;
      $tmp['specifics']=$specifics;
      $tmp['issue']=$issue;
      $tmp['comment']=$comment;
      $tmp['datecreated']=$datecreated;
      $tmp['status']=$status;
      $tmp['attrid']=$attrid;
      array_push($data,$tmp);
      }
      return $data;
    }
    
    public function loaduserreview($userid){
      $stmt = $this->con->prepare("select userid, rating, review, datecreated, attrname, attrid from rate natural join touristattraction where userid = ? limit 5");
      $stmt->bind_param("i", $userid);
      $stmt->execute();
      
      $stmt->bind_result($userid,$rating,$review,$datecreated,$attrname,$attrid);
  
      $data=array();
      while($stmt->fetch()){
      $tmp=array();
      $tmp['userid'] = $userid;
      $tmp['attrname']=$attrname;
      $tmp['rating']=$rating;
      $tmp['review']=$review;
      $tmp['datecreated']=$datecreated;
      $tmp['attrid']=$attrid;
      array_push($data,$tmp);
      }
      return $data;
    }
  
    public function loaduserreport($userid){
      $stmt = $this->con->prepare("select userid, reportid,specifics, issue, comment, datecreated, status, attrname, attrid from report NATURAL JOIN touristattraction where userid = ? limit 5");
      $stmt->bind_param("i", $userid);
      $stmt->execute();
      
      $stmt->bind_result($userid,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrname,$attrid);
  
      $data=array();
      while($stmt->fetch()){
      $tmp=array();
      $tmp['userid'] = $userid;
      $tmp['attrname']=$attrname;
      $tmp['reportid']=$reportid;
      $tmp['specifics']=$specifics;
      $tmp['issue']=$issue;
      $tmp['comment']=$comment;
      $tmp['datecreated']=$datecreated;
      $tmp['status']=$status;
      $tmp['attrid']=$attrid;
      array_push($data,$tmp);
      }
      return $data;
    }
  
    public function getAttractions(){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid)"); 
      return $stmt;
    }

    public function getAttrSearch($query){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid) where attrname like '%".$query."%' or location like '%".$query."%' or category like '%".$query."%';"); 
      return $stmt;
    }

    public function getAttractionsById($id){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid) where attrid = ?"); 
      $stmt->bind_param("i", $id);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($attrid,$attrname,$location,$phonenumber,$schedule,$category,$rating);
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['attrid'] = $attrid;
          $tmp['attrname'] = $attrname;
          $tmp['location'] = $location;
          $tmp['phonenumber'] = $phonenumber;
          $tmp['schedule'] = $schedule;
          $tmp['category'] = $category;
          $tmp['rating'] = $rating;
          $data_cat = $this->getFacilityByAttrID($attrid);
          $tmp['facility']=$data_cat;
          $image = $this->attrimage($attrid);
          $tmp['image'] = $image;
          return $tmp;
      }
    }

    public function getTopRatedAttraction(){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid) order by rating desc;"); 
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($attrid,$attrname,$location,$phonenumber,$schedule,$category,$rating);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['attrid'] = $attrid;
          $tmp['attrname'] = $attrname;
          $tmp['location'] = $location;
          $tmp['phonenumber'] = $phonenumber;
          $tmp['schedule'] = $schedule;
          $tmp['category'] = $category;
          $tmp['rating'] = $rating;
          $data_cat = $this->getFacilityByAttrID($attrid);
          $tmp['facility']=$data_cat;
          $image = $this->attrimage($attrid);
          $tmp['image'] = $image;
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getLimitedTopRatedAttraction($limit){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid) order by rating desc limit ? ;"); 
      $stmt->bind_param("i", $limit);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($attrid,$attrname,$location,$phonenumber,$schedule,$category,$rating);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['attrid'] = $attrid;
          $tmp['attrname'] = $attrname;
          $tmp['location'] = $location;
          $tmp['phonenumber'] = $phonenumber;
          $tmp['schedule'] = $schedule;
          $tmp['category'] = $category;
          $tmp['rating'] = $rating;
          $data_cat = $this->getFacilityByAttrID($attrid);
          $tmp['facility']=$data_cat;
          $image = $this->attrimage($attrid);
          $tmp['image'] = $image;
          array_push($data,$tmp);
      }
      return $data;

    }

    public function getReportByUserId($userid){
      $stmt = $this->con->prepare("select * from report where userid = ?"); 
      $stmt->bind_param("i", $userid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['reportid'] = $reportid;
          $tmp['specifics'] = $specifics;
          $tmp['issue'] = $issue;
          $tmp['comment'] = $comment;
          $tmp['datecreated'] = $datecreated;
          $tmp['status'] = $status;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reportImage($reportid);
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getReportByAttrId($attrid){
      $stmt = $this->con->prepare("select * from report where attrid = ?"); 
      $stmt->bind_param("i", $attrid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['reportid'] = $reportid;
          $tmp['specifics'] = $specifics;
          $tmp['issue'] = $issue;
          $tmp['comment'] = $comment;
          $tmp['datecreated'] = $datecreated;
          $tmp['status'] = $status;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reportImage($reportid);
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getReportByUserIdAndAttrId($attrid,$userid){
      $stmt = $this->con->prepare("select * from report where attrid = ? and userid = ?"); 
      $stmt->bind_param("ii", $attrid,$userid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['reportid'] = $reportid;
          $tmp['specifics'] = $specifics;
          $tmp['issue'] = $issue;
          $tmp['comment'] = $comment;
          $tmp['datecreated'] = $datecreated;
          $tmp['status'] = $status;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reportImage($reportid);
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getReportByReportId($reportid){
      $stmt = $this->con->prepare("select * from report where reportid = ?"); 
      $stmt->bind_param("i", $reportid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$reportid,$specifics,$issue,$comment,$datecreated,$status,$attrid);
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['reportid'] = $reportid;
          $tmp['specifics'] = $specifics;
          $tmp['issue'] = $issue;
          $tmp['comment'] = $comment;
          $tmp['datecreated'] = $datecreated;
          $tmp['status'] = $status;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reportImage($reportid);
          return $tmp;
      }
    }

    public function getReviewByUserId($userid){
      $stmt = $this->con->prepare("select * from rate where userid = ?"); 
      $stmt->bind_param("i", $userid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$rating,$review,$datecreated,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['rating'] = $rating;
          $tmp['review'] = $review;
          $tmp['datecreated'] = $datecreated;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reviewImage($attrid,$userid);
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getReviewByAttrId($attrid){
      $stmt = $this->con->prepare("select * from rate where attrid = ?"); 
      $stmt->bind_param("i", $attrid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$rating,$review,$datecreated,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['rating'] = $rating;
          $tmp['review'] = $review;
          $tmp['datecreated'] = $datecreated;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reviewImage($attrid,$userid);
          array_push($data,$tmp);
      }
      return $data;
    }

    public function getReviewByUserIdAndAttrId($attrid, $userid){
      $stmt = $this->con->prepare("select * from rate where attrid = ? and userid = ?"); 
      $stmt->bind_param("ii", $attrid,$userid);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($userid,$rating,$review,$datecreated,$attrid);
      $data = array();
      while($stmt->fetch()){    
          $tmp=array();
          $tmp['user'] = $this->getUserById($userid);
          $tmp['rating'] = $rating;
          $tmp['review'] = $review;
          $tmp['datecreated'] = $datecreated;
          $tmp['attraction']=$this->getAttractionsById($attrid);
          $tmp['image'] = $this->reviewImage($attrid,$userid);
          array_push($data,$tmp);
      }
      return $data;
    }

    












  
    public function getFacilityByAttrID($attrid){
      $stmts = $this->con->prepare("SELECT facility FROM havefacility NATURAL JOIN facilitylist WHERE attrid = ?");
      $stmts->bind_param("i", $attrid);
      $stmts->execute();
      $stmts->bind_result($category);
      $cat=array();
      while($stmts->fetch()){
      $tmp = $category;
      array_push($cat,$tmp);
      }            
      return $cat;
    }
    
    public function attrsearch($key){
      $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid) where attrname like '%?%' or location like '%?%'");
      $stmt->bind_param("ss", $key, $key);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($attrid,$attrname,$location,$details,$schedule,$category,$rating);
      $response_data = array();
    
      $data=array();
      while($stmt->fetch()){    
      $tmp=array();
      $tmp['attrid'] = $attrid;
      $tmp['attrname']=$attrname;
      $tmp['location']=$location;
      $tmp['details']=$details;
      $tmp['schedule']=$schedule;
      $tmp['category']=$category;
      $tmp['rating']=$rating;
      $data_cat = $db->getFacilityByAttrID($attrid);
      $tmp['facility']=$data_cat;
      array_push($data,$tmp);
      }
      return $data;
    }

    public function loadAttractions(){
      $stmt = $this->con->prepare("SELECT * FROM touristattraction;");
      $stmt->execute();
      
      $stmt->bind_result($attrid,$attrname,$location,$details,$schedule);
      $data=array();
      while($stmt->fetch()){
        $tmp=array();
        $tmp['attrid'] = $attrid;
        $tmp['attrname']=$attrname;
        $tmp['location']=$location;
        $tmp['phonenumber']=$details;
        $tmp['schedule']=$schedule;
        array_push($data,$tmp);
      }
      return $data;
    }

    public function attractionDetails($id){
      $image = $this->attrImage($id);
      $data['image'] = $image;

      $stmt = $this->con->prepare("SELECT * FROM touristattraction where attrid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();
      
      $stmt->bind_result($attrid,$attrname,$location,$details,$schedule);
      $data = array();
      while($stmt->fetch()){
        $tmp=array();
        $tmp['attrid'] = $attrid;
        $tmp['attrname']=$attrname;
        $tmp['location']=$location;
        $tmp['phonenumber']=$details;
        $tmp['schedule']=$schedule;
        $tmp['image'] = $image;
        array_push($data,$tmp);
      }
      return $data;
    }

    public function insertAttrImage($id,$filename){
      $stmt = $this->con->prepare("insert into image values (?,?);");
      $stmt->bind_param("is",$id,$filename);
      return $stmt->execute();
    }

    public function insertReportImage($attrid,$userid,$reportid,$imagepath){
      $stmt = $this->con->prepare("insert into imagereport values (?,?,?,?);");
      $stmt->bind_param("iiis",$attrid,$userid,$reportid,$imagepath);
      return $stmt->execute();
    }

    public function insertReviewImage($attrid,$userid,$imagepath){
      $stmt = $this->con->prepare("insert into imagereview values (?,?,?);");
      $stmt->bind_param("iis",$attrid,$userid,$imagepath);
      return $stmt->execute();
    }

    public function updateReviewImage($attrid,$userid,$imagepath){
      $stmt = $this->con->prepare("update imagereview set imagepath=? where attrid = ? and userid = ?");
      $stmt->bind_param("sii", $imagepath,$attrid,$userid);
      return $stmt->execute();
    }

    public function deleteReviewImage($attrid,$userid){
      $stmt = $this->con->prepare("delete from imagereview where attrid = ? and userid = ?");
      $stmt->bind_param("ii",$attrid,$userid);
      return $stmt->execute();
    }

    public function getLastAttrImageIndex($id){
      $stmt = $this->con->prepare("select count(*) as count from image where attrid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($index);
      $stmt->fetch();
      return $index;
    }

    public function getLastReportImageIndex($id){
      $stmt = $this->con->prepare("select count(*) as count from imagereport where attrid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($index);
      $stmt->fetch();
      return $index;
    }

    public function getLastReviewImageIndex($id){
      $stmt = $this->con->prepare("select count(*) as count from imagereview where attrid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($index);
      $stmt->fetch();
      return $index;
    }

    public function getLastReportId(){
      $stmt = $this->con->prepare("select max(reportid) as max from report;");
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($max);
      $stmt->fetch();
      return $max;
    }

    public function getLastReviewId(){
      $stmt = $this->con->prepare("select max(reviewid) as max from review;");
      $stmt->execute();
      $stmt->store_result();
      $stmt->bind_result($max);
      $stmt->fetch();
      return $max;
    }

    public function attrImage($id){
      $stmt = $this->con->prepare("SELECT * FROM image where attrid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();

      $stmt->bind_result($attrid,$url);
      $data = array();
      while($stmt->fetch()){
        array_push($data,$url);
      }

      return $data;
    }

    public function reportImage($id){
      $stmt = $this->con->prepare("SELECT * FROM imagereport where reportid = ?;");
      $stmt->bind_param("i",$id);
      $stmt->execute();

      $stmt->bind_result($attrid,$userid,$reportid,$url);
      $data = array();
      while($stmt->fetch()){
        array_push($data,$url);
      }

      return $data;
    }

    public function reviewImage($attrid,$userid){
      $stmt = $this->con->prepare("SELECT * FROM imagereview where attrid = ? and userid = ?;");
      $stmt->bind_param("ii",$attrid,$userid);
      $stmt->execute();

      $stmt->bind_result($attrid,$userid,$url);
      $stmt->fetch();
  
      return $url;
    }

  }