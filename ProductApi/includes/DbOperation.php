<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
	

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/
	function getProducts(){
		$stmt = $this->con->prepare("SELECT id, productName, productCategory, sellType FROM products");
		$stmt->execute();
		$stmt->bind_result($id, $productName, $productCategory, $sellType);
		
		$products = array(); 
		
		while($stmt->fetch()){
			$product  = array();
			$product['id'] = $id; 
			$product['productName'] = $productName; 
			$product['productCategory'] = $productCategory; 
			$product['sellType'] = $sellType; 
			
			array_push($products, $product); 
		}
		
		return $products; 
	}
	

}