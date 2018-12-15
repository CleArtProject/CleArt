<?php

    class dbOperation{
        private $con;
        
        function __construct(){
            require dirname(__FILE__).'/dbConnect.php';
                        
            $db = new dbConnect;
            $this->con= $db->connect();
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

        public function newreport($userid,$specific,$issues,$comment,$attrid){
            $stmt = $this->con->prepare("insert into report (userid,specifics,issue,comment,attrid) values (?,?,?,?,?)");
            $stmt->bind_param("isssi",$userid,$specific,$issues,$comment,$attrid);
            if($stmt->execute()){   
                return REPORT_CREATED;
            }else{
                return REPORT_FAILURE;
            }
        }

        public function newreview($userid,$rating,$review,$attrid){
            if($this->isUserReview($userid,$attrid)){
                $stmt = $this->con->prepare("update rate set rating = ?, review = ? where userid=? and attrid=?;");
                $stmt->bind_param("isii",$rating,$review,$userid,$attrid);
            }else{
                $stmt = $this->con->prepare("insert into rate (userid,rating,review,attrid) values (?,?,?,?)");
                $stmt->bind_param("iisi",$userid,$rating,$review,$attrid);
            }
            if($stmt->execute()){   
                return REVIEW_CREATED;
            }else{
                return REVIEW_FAILURE;
            }
        }
        

        public function isUserReview($userid,$attrid){
            $stmt = $this->con->prepare("select review from rate where userid=? and attrid =?");
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
            $stmt = $this->con->prepare("SELECT userid,name,email,phonenum,username FROM userdata WHERE userid = ?");
            $stmt->bind_param("i", $userid);
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

        public function loaddatadetail(){
            $stmt = $this->con->prepare("select * FROM attr_detail_category left JOIN rating using (attrid)"); 
            return $stmt;
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

    }