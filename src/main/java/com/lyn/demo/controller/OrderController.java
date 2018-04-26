package com.lyn.demo.controller;

import com.lyn.demo.domain.Order;
import com.lyn.demo.enums.ResultEnum;
import com.lyn.demo.result.Result;
import com.lyn.demo.service.OrderService;
import com.lyn.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    //根据用户ID查询订单
    @GetMapping("/order/user/getorderlist")
    public Result userFindByOrderId(@RequestParam("userId") Integer userId){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.findByUserId(userId));
    }

    //根据商家ID查询订单
    @GetMapping("/order/seller/getorderlist")
    public Result sellerFindByOrderId(@RequestParam("sellerId") Integer sellerId){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.findBySellerId(sellerId));
    }

    //根据订单号查询订单
    @GetMapping("order/getone")
    public Result findOrderByOrderId(@RequestParam("orderId") String orderId){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.findByOderId(orderId));
    }

    //创建订单
    @PostMapping("oder/save")
    public  Result addOrder(Integer sellerId,Integer userid, List<Integer> commodityid, String adress, List<Integer> number, List<Double> price){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.addOrder(sellerId,userid,commodityid,adress,number,price));
    }

    //删除订单
    @DeleteMapping("/order/delete")
    public Result deleteOrder(@RequestParam("orderId") String orderId) throws Exception {
        orderService.deleteOrder(orderId);
        return ResultUtils.wrapResult(ResultEnum.SUCESS,null);
    }

    //商家发货或修改快递单号
    @PutMapping("order/deliver")
    public Result updateDeliver(String orderid,String deliverFirm,String ordernumber){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.updateDeliver(orderid,deliverFirm,ordernumber));
    }

    //根据用户ID查询订单，分页获取订单列表   默认十条数据
    @GetMapping("order/userorderlist")
    public Result userGetOrderListByUserId(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNo",required = false) Integer pageNo){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.userQueryOrder(userId,pageNo==null?1:pageNo,pageSize).getList());
    }

    //根据商家ID查询订单，分页获取订单列表   默认十条数据
    @GetMapping("order/sellerorderlist")
    public Result sellerGetOrderListByUserId(@RequestParam("sellerId") Integer sellerId,
                                           @RequestParam(value = "pageNo",required = false) Integer pageNo){
        return ResultUtils.wrapResult(ResultEnum.SUCESS,orderService.userQueryOrder(sellerId,pageNo==null?1:pageNo,pageSize).getList());
    }

}
