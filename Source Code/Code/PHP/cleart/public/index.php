<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';
require '../include/dbOperation.php';

$app = new \Slim\App(
    ['settings'=>['displayErrorDetails'=>true]]
);

$container = $app->getContainer();
$container['upload_directory'] = __DIR__ . '/images';

$app->post('/createuser',function(Request $request, Response $response){
    if(!inputChecker(array('name','username','email','phonenum','password'),$request,$response)){
        $request_data = $request->getParsedBody(); 

        $name = $request_data['name'];
        $username = $request_data['username'];
        $email = $request_data['email'];
        $phonenum = $request_data['phonenum'];
        $password = $request_data['password'];
        
        $hash_password = md5($password);
        $db = new DbOperation; 

        $result = $db->createUser($name,$username,$email,$phonenum,$hash_password);

        if ($result == USER_CREATED){   
            $message = array();
            $message['error'] = false;
            $message['message'] = 'new user created';
            
            $response->write(json_encode($message));

            return $response
                    -> withHeader('content-type','application/json')
                    -> withStatus(201);

        }else if ($result == USER_FAILURE){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'some error occured';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }else if ($result==USER_EXIST){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'username already in use, please insert another username';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }else if ($result==EMAIL_EXIST){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'email already exist, please use another email';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }else if ($result==PHONENUM_EXIST){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'phone number already exist, please use another phone number';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }
    }
    return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);
});

$app->post('/updateuser',function(Request $request, Response $response){
    if(!inputChecker(array('name','username','email','phonenum'),$request,$response)){
        $request_data = $request->getParsedBody(); 
		
        $name = $request_data['name'];
        $username = $request_data['username'];
        $email = $request_data['email'];
        $phonenum = $request_data['phonenum'];
		$userid = $request_data['userid'];
        
        $db = new DbOperation; 
        $result = $db->updateUser($name,$username,$email,$phonenum,$userid);
        if($result==USER_UPDATED){
            $response_data = array(); 
            $response_data['error'] = false; 
            $response_data['message'] = 'User Updated Successfully';
            $user = $db->getUserById($userid);
            $response_data['user'] = $user; 
            $response->write(json_encode($response_data));
            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
        }else if($result==USER_FAILURE){
            $response_data = array(); 
            $response_data['error'] = true; 
            $response_data['message'] = 'Please try again later';
            $user = $db->getUserById($userid);
            $response_data['user'] = $user; 
             $response->write(json_encode($response_data));
             return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
              
        }
    }
    return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);
});

$app->post('/newreport',function(Request $request, Response $response){
    if(!inputChecker(array('specific','issues','comment'),$request,$response)){
        $request_data = $request->getParsedBody(); 

        $userid = $request_data['userid'];
        $specific = $request_data['specific'];
        $issues = $request_data['issues'];
        $comment = $request_data['comment'];
        $attrid = $request_data['attrid'];
                    
        $db = new DbOperation; 

        $result = $db->newreport($userid,$specific,$issues,$comment,$attrid);

        if ($result == REPORT_CREATED){   
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Report Successfully Added';
            
            $response->write(json_encode($message));

            return $response
                    -> withHeader('content-type','application/json')
                    -> withStatus(201);

        }else if ($result == REPORT_FAILURE){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some Error Occured, Please Try Again';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }
    }
    return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);
});

$app->post('/newreview',function(Request $request, Response $response){
    if(!inputChecker(array('rating','review'),$request,$response)){
        $request_data = $request->getParsedBody(); 

        $userid = $request_data['userid'];
        $rating = $request_data['rating'];
        $review = $request_data['review'];
        $attrid = $request_data['attrid'];
                    
        $db = new DbOperation; 

        $result = $db->newreview($userid,$rating,$review,$attrid);

        if ($result == REVIEW_CREATED){   
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Review Successfully Added';
            
            $response->write(json_encode($message));

            return $response
                    -> withHeader('content-type','application/json')
                    -> withStatus(201);

        }else if ($result == REVIEW_FAILURE){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some Error Occured, Please Try Again';
            
            $response->write(json_encode($message));

            return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);

        }
    }
    return $response
                    ->withHeader('content-type','application/json')
                    ->withStatus(422);
});

$app->post('/userlogin', function(Request $request, Response $response){
    if(!inputChecker(array('username', 'password'), $request, $response)){
        $request_data = $request->getParsedBody(); 

        $username = $request_data['username'];
        $password = $request_data['password'];
        
        $db = new DbOperation;

        $result = $db->userLogin($username, $password);
        if($result == USER_AUTHENTICATED){
            
            $user = $db->getUserByUsername($username);
            $response_data = array();
            $response_data['error']=false; 
            $response_data['message'] = 'Login Successful';
            $response_data['user']=$user; 
            $response->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);    
        }else if($result == USER_NOT_FOUND){
            $response_data = array();
            $response_data['error']=true; 
            $response_data['message'] = 'User not exist';
            $response->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);    
        }else if($result == USER_PASSWORD_DO_NOT_MATCH){
            $response_data = array();
            $response_data['error']=true; 
            $response_data['message'] = 'Invalid credential';
            $response->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);  
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});

$app->get('/loaddata', function(Request $request, Response $response){
    $db = new DbOperation;

    $result = $db->loaddata();
    $response_data = array();
    $response_data['error']=false;  
    $response_data['attraction']=$result;
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);  
});

$app->get('/loaddata/detail', function(Request $request, Response $response){
    $db = new DbOperation;

    $result_attr = $db->loaddatadetail();
    $result_attr->execute();
    $result_attr->store_result();
    $result_attr->bind_result($attrid,$attrname,$location,$details,$schedule,$category,$rating);
    $response_data = array();

    $response_data['error']=false;  

    $data=array();
    while($result_attr->fetch()){    
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

    $response_data['attraction']=$data;
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);  
});

$app->post('/loadattrreview', function(Request $request, Response $response){
    if(!inputChecker(array('attrid'),$request,$response)){
        $request_data = $request->getParsedBody();
        $attrid = $request_data['attrid'];
        $db = new DbOperation;

        $result = $db->loadattrreview($attrid);

        $response_data = array();
        $response_data['error']=false;  
        $response_data['review']=$result;
        $response->write(json_encode($response_data));
        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200); 
    } 
});

$app->post('/loadattrreport', function(Request $request, Response $response){
    if(!inputChecker(array('attrid'),$request,$response)){
        $request_data = $request->getParsedBody();
        $attrid = $request_data['attrid'];
        $db = new DbOperation;

        $result = $db->loadattrreport($attrid);

        $response_data = array();
        $response_data['error']=false;  
        $response_data['report']=$result;
        $response->write(json_encode($response_data));
        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200); 
    } 
});

$app->post('/loaduserreview', function(Request $request, Response $response){
    if(!inputChecker(array('userid'),$request,$response)){
        $request_data = $request->getParsedBody();
        $userid = $request_data['userid'];
        $db = new DbOperation;

        $result = $db->loaduserreview($userid);

        $response_data = array();
        $response_data['error']=false;  
        $response_data['review']=$result;
        $response->write(json_encode($response_data));
        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200); 
    } 
});

$app->post('/loaduserreport', function(Request $request, Response $response){
    if(!inputChecker(array('userid'),$request,$response)){
        $request_data = $request->getParsedBody();
        $userid = $request_data['userid'];
        $db = new DbOperation;

        $result = $db->loaduserreport($userid);

        $response_data = array();
        $response_data['error']=false;  
        $response_data['report']=$result;
        $response->write(json_encode($response_data));
        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200); 
    } 
});

$app->get('/attrsearch', function(Request $request, Response $response){
    $request_data = $request->getParsedBody();
    if(!isset($request_data['key'])||strlen($request_data['key'])<=0){
        $db = new DbOperation;

        $result_attr = $db->loaddatadetail();
        $result_attr->execute();
        $result_attr->store_result();
        $result_attr->bind_result($attrid,$attrname,$location,$details,$schedule,$category,$rating);
        $response_data = array();

        $data=array();
        while($result_attr->fetch()){    
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
        $result = $data;
    
    }else{
        $key = $request_data['key'];
        $db = new DbOperation;  

        $result = $db->attrsearch($key); 
    }
    $response_data = array(); 
    $response_data = $result;
    $response->write(json_encode($response_data)); 
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);  
});


function inputChecker ($required_params,$request,$response){
    $error = false; 
    $error_params = '';
    $request_params = $request->getParsedBody(); 
    foreach($required_params as $param){
        if(!isset($request_params[$param]) || strlen($request_params[$param])<=0){
            $error = true; 
            $error_params .= $param . ', ';
        }
    }
    if($error){
        $error_detail = array();
        $error_detail['error'] = true; 
        $error_detail['message'] = 'Required parameters ' . substr($error_params, 0, -2) . ' are missing or empty';
        $response->write(json_encode($error_detail));
    }
    return $error;
    
}

function moveUploadedFile($directory, Slim\Http\UploadedFile $uploadedFile)
{
    $extension = pathinfo($uploadedFile->getClientFilename(), PATHINFO_EXTENSION);
    $basename = bin2hex(random_bytes(8));
    $filename = sprintf('%s.%0.8s', $basename, $extension);

    $uploadedFile->moveTo($directory . DIRECTORY_SEPARATOR . $filename);

    return $filename;
}


$app->run(); 