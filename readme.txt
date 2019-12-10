
	笔记链接：https://app.yinxiang.com/shard/s44/nl/25772775/d3658dbb-609d-464f-9b81-ddf52c552f53
	https://app.yinxiang.com/shard/s44/nl/25772775/0c769a53-be25-4bb1-8475-753734bd2980

=================================================================================================

	* 要想分页查询，那么必然要使用  ELECT * FROM emp LIMIT  参数1（startIndex），参数2（pageSize）；
	*
规律如下

SELECT * FROM emp LIMIT 0,5;-- 第一页的数据
SELECT * FROM emp LIMIT 5,5;-- 第二页的数据
SELECT * FROM emp LIMIT 10,5;-- 第三页的数据
SELECT * FROM emp LIMIT (n-1)*pageSize,pageSize;-- 第n页的数据
而这两个参数dao层不会自己产生，需要service传递，同时这两个参数又是从servlet传递过来，而servlet是从也页面接受参数。经过层层 传递
但是，页面上用户只看到的是页码  pageNumber，传递到servlet，servlet传递到service，在service层进行逻辑处理，将  pageNumber 和 startIndex 扯上关系，
关系就是  startIndex =  (pageNumber -1)*pageSize
好了，到此为止，简单的分页思想就出来了，每次访问数据展示的页面（增删改查），都要传递一个参数pageNumber，经过层层传递，以及数据处理，最终将startIndex传递到数据库，从而取出数据，返回

============================================================================================================
复杂分页

	*
一个页码栏有以上几种，首尾页，上下页，总条数，总页数
	*
总记录数和页数应该是每次查询的时候就应该 从后台取到，并传递过来

		*
所以现在需要传递的参数不能单单的一个   list数据 还需要，
		*
1。总记录数  totalCount
		*
2。总页码     totalPage
		*
3。然后加上list数据   list<emp>
		*
4。需要一个当前页面，为了分页查询传递参数
		*
5。需要一个 每页多少行pageSize （目前写死）
		*
所以service在给servlet传递数据的时候应该包含这几个参数
		*
service的数据又是经过dao层，dao从数据库 取数据

			*
所以dao层   totalCount ：select count(*) from emp
			*
 totalPage :  totalCount%pageSize 是否有余数？相除+1：相除
	*
现在我们知道，应该给页面传递若干个数据，但是怎么传合适呢？

		*
java万物皆对象，所以想到，将这几个参数封装到类中
		*

	*

最后开始考虑点击第几页，就显示第几页的数据

		*
首页，那就是第一页，那就可以直接当第一页使href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=1"
		*
同理  尾页就当最后一页使  href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=最后一页，直接在pagebean里边取"
		*
上一页，那就是当前页减一，刚好pagebean里边有个 pagenumber
		*
下一页当前页 加一
	*
我们每次点击那一页，肯定是要传递参数，那么就直接给一个 超链接，连接到查询数据的servlet

		*
href="${pageContext.request.contextPath}/EmpServlet?action=findAll&pageNumber=当前第几页
	*
【注意】分页查询，每次都会传递一个参数  叫pageNumber，之前的增、删、改，都是要重定向到查询的servlet，所以，分页查询中增删改，也是需要传递一个pageNumber，要不然会报错，因为查询的servlet需要获取到页码，从而层层传递给数据库，进行查询

		*

	*
【注意】如果现在 处于第一页，那么再点上一页，此时页码会传递一个 0，而 limit函数是不接受0的，所以，不如如果是第一页 ，就直接不显示上一页

		*
类比 百度
		*
可以判断一下当前页是不是第一页，直接隐藏，下一页同理
	*
【注意】由于超链接提交  在地址栏可以看到参数的传递，所以是可以认为修改 pagenumber的数据，改成字母之类的错误数据
	*


		*
所以需要在servlet（因为需要在servlet中获取参数）中进行异常处理
		*



