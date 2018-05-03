package com.lyn.demo.controller;

import com.lyn.demo.domain.Order;
import com.lyn.demo.enums.ResultEnum;
import com.lyn.demo.result.Result;
import com.lyn.demo.service.OrderService;
import com.lyn.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    //根据用户ID查询订单
    @GetMapping("/user/getorderlist")
    public Result userFindByOrderId(@RequestParam("userId") Integer userId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.findByUserId(userId));
    }

    //根据商家ID查询订单
    @GetMapping("/seller/getorderlist")
    public Result sellerFindByOrderId(@RequestParam("sellerId") Integer sellerId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.findBySellerId(sellerId));
    }

    //根据订单号查询订单
    @GetMapping("/getone")
    public Result findOrderByOrderId(@RequestParam("orderId") String orderId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.findByOderId(orderId));
    }

    //创建订单
    @PostMapping("/user/save")
    public  Result addOrder(@RequestParam("sellerId") Integer sellerId,
                            @RequestParam("userId") Integer userId,
                            @RequestParam("adress") String adress,
                            @RequestParam("commodityId") List<Integer> commodityId,
                            @RequestParam("number") List<Integer> number,
                            @RequestParam("price") List<Double> price){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.addOrder(sellerId,userId,adress,commodityId,number,price));
    }

    //测试添加orderdetail
    @PostMapping("/addorderdetail")
    public Result addorderdetail(@RequestParam("id") Integer id,
                                 @RequestParam("number") Integer number,
                                 @RequestParam("orderId") String orderId,
                                 @RequestParam("commodityId") Integer commodityId,
                                 @RequestParam("price") Double price){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.addorderdetail(id,number,orderId,commodityId,price));
    }

    //卖家删除订单
    @DeleteMapping("/seller/delete")
    public Result deleteOrder(@RequestParam("orderId") String orderId) throws Exception {
        orderService.deleteOrder(orderId);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,null);
    }

    //买家删除订单显示
    @PutMapping("/user/delete")
    public Result deleteUserOrder(@RequestParam("orderId") String orderId)throws  Exception{
        orderService.deleteUserOrder(orderId);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,null);
    }

    //商家发货或修改快递单号
    @PutMapping("/seller/deliver")
    public Result updateDeliver(@RequestParam String orderId,
                                @RequestParam String deliverFirm,
                                @RequestParam String deliverNumber){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.updateDeliver(orderId,deliverFirm,deliverNumber));
    }

    //根据用户ID查询订单，分页获取订单列表   默认十条数据
    @GetMapping("/user/userorderlist")
    public Result userGetOrderListByUserId(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNumber",required = false) Integer pageNumber){
        pageNumber = pageNumber == null ? 1:pageNumber;
        Page<Order> orderList = orderService.userQueryOrder(userId,pageNumber,pageSize);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderList.getContent());
    }

    //根据商家ID查询订单，分页获取订单列表   默认十条数据
    @GetMapping("/seller/sellerorderlist")
    public Result sellerGetOrderListBySellerId(@RequestParam("sellerId") Integer sellerId,
                                           @RequestParam(value = "pageNumber",required = false) Integer pageNumber){
        pageNumber = pageNumber == null ? 1:pageNumber;
        Page<Order> orderList = orderService.sellerQueryOrder(sellerId,pageNumber,pageSize);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderList.getContent());
    }

    //卖家付款
    @PutMapping("/user/pay")
    public Result userPay(@RequestParam("orderId") String orderId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.orderPay(orderId));
    }

    //买家确认收货
    @PutMapping("/user/confirmreceipt")
    public Result userConfirmReceipt(@RequestParam("orderId") String orderId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,orderService.confirmReceipt(orderId));
    }

}
