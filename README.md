# 最终实践:微博app

### 第一天要求:

> - 闪屏⻚
>
> - [x] ⽤⼾第⼀次启动app时显⽰隐私内容，后续再次打开app将不再显⽰隐私内容了；状态保存在本地
> - [x] 卸载或者清除本地数据后，会重复步骤1；
> - [x] 点击同意并使⽤，进⼊app⾸⻚，点击不同意，退出app
> - [x] ⽤⼾协议和隐私协议，点击弹出Toast "查看⽤⼾协议"、"查看隐私协议"
> - [x] 通过Activity转场动画，闪屏⻚⾃然过渡到⾸⻚，动画⾃由发挥

> - 我的
>
> - [x] 未登录时，显⽰点击登录，点击默认头像和点击登录，跳转到登录⻚⾯
> - [x] ⽤⼾已登录，显⽰⽤⼾信息，如头像和⽤⼾名；粉丝数没有就隐藏
> - [x] 点击退出登录，删除本地缓存token，更新为未登录状态

> - 登录⻚⾯
>
> - [x] ⼿机号码限制11位，仅⽀持输⼊数字；⼿机号码输⼊到11位后⽅可允许点击获取验证码，否则弹出Toast提⽰"请输⼊完整⼿机号"
> - [x] 获取验证码，要设置防⽌多次点击；在获取验证码倒计时结束前，不能响应点击事件；注意短信验证码每天只能获取20次；
> - [x] 倒计时60s，倒计时结束后，重新显⽰获取验证码
> - [x] 验证码限制6位，仅⽀持输⼊数字
> - [x] ⼿机号码和验证码均输⼊完成后，登录按钮才处于可点击状态，否则制灰
> - [x] 登录成功后，将token保存在本地即可，后续再次启动app，不再登录

### 第二天要求:

> - ⾸⻚
>
> - [x] ⾸⻚单条帖⼦包含以下元素：头像、⽤⼾昵称、删除按钮、帖⼦标题、视频或者图⽚、评论、点赞；
> - [x] ⾸⻚⽀持下拉刷新和上拉加载更多；下拉刷新：每次请求成功后，将数据顺序随机打乱，模拟数据更新；上拉加载没有数据，则弹出Toast：⽆更多内容；
> - [x] 帖⼦标题：普通⽂本，最多显⽰6⾏，超过....
> - [x] 图⽚模块：单张图⽚⼤图显⽰，⼤图尺⼨固定；多张图⽚使⽤9宫格即可
> - [x] 单张图⽚的宽⾼使⽤Glide动态获取，根据宽>⾼，显⽰横图样式，否则显⽰竖图样式
>
> 视频模块：
>
> - [x] 视频默认不⾃动播放，点击视频播放，只有进度条，不可拖拽；视频划出屏幕外，⾃动暂停；
> - [x]  视频设置循环播放，视频没有声⾳
> - [x] 视频显⽰规则：默认显⽰横图样式
> - [x]  视频功能使⽤MediaPlayer实现，视频播放之前仅显⽰封⾯图，暂停状态：点击视频开始播放；播放状态:点击视频暂停播放；
> - [x] 进度条跟随视频播放进度不断更新，通过视频当前播放时间和总时间，动态更新进度条进度
> - [x] 更新视频播放时⻓
>
> - [x] 视频模块和图⽚模块判断：优先判断videoUrl字段不为空，则视频模块；images不为空，则图⽚模块；均为空，则是普通⽂本；
> - [x] 点击删除按钮，删除当前Item，仅更新本地数据；下拉刷新后，数据继续显⽰
> - [x] 点击评论按钮：弹出Toast：点击第N条数据评论按钮
> - [x] 点击图⽚：弹出Toast：我是第N张图⽚，当前帖⼦总图⽚数量为M张；
> - [x] 登录成功后，刷新⾸⻚数据
> - [x] 未登录和已登录⾸⻚均可正常显⽰

### 第三天要求:

> - ⾸⻚
>
> - [x] 点赞：点赞和取消点赞动画，⽹络请求成功后更新本地数据状态和UI；点赞和取消点赞必须登录，没有登录跳转到登录⻚⾯；参考今⽇头条点赞效果，略有简化
> - [x] 点赞动画：图标由1.0放⼤到1.2倍，然后回到1.0倍，于此同时图标沿Y轴旋转360度；持续时间1000ms
> - [x] 取消点赞动画：图标由1.0缩⼩到0.8倍，然后回到1.0倍，持续时间1000ms
> - [x] 点击图⽚：跳转到⼤图浏览⻚，点击当前图⽚跳转过去后当前⻚要对应，例如：点击第三张图⽚，⼤图浏览⻚默认显⽰第三张，同时当前⻚⾯index=3；

> - ⼤图浏览⻚
>
> - [x] 仅显⽰⽤⼾头像、昵称，当前⻚数/总图⽚数、下载按钮
> - [x] ⻚⾯全屏显⽰，隐藏顶部状态栏和底部导航栏；⻚⾯要求渐⼊渐出，效果⾃然
> - [x] 图⽚以宽度为基础，⾼度⾃适应；⾼度⼩于屏幕⾼度则上下居中
> - [x] 可左右滑动，左右滑动时当前⻚数更新；
> - [x] 点击屏幕⻚⾯关闭，返回⾸⻚
> - [x] 点击下载：图⽚下载后保存到相册中，后台下载即可，下载完成弹出的Toast：图⽚下载完成，请相册查看

### 完成说明:

##### 录屏结果路径:work_result(包括录屏和apk)

1. 花在UI上面的时间挺多的,但个人认为完成的并不好,深入学习了一下发现有很多方法能让UI设计更方便效果更好,所以UI还需继续打磨
2. 网络请求花了我不少时间,主要当时课上听的不好,没弄明白,不过现在差不多掌握了.主要我当时没想那么多,就一个请求一个请求写,不过这样写起来不够美观和方便,以后记得把网络单独请求放一起方便管理和维护.
3. 上拉加载有点问题,就是加载了但加载的很奇怪,过度不平滑,可能因为我没设置上拉动画和加载时随机打乱数据有关
4. 视频播放是没学过的知识,特别学了一下,效果还可以,本来还想实现放大播放的,不过没要求,我以后自己加上吧
5. 大图浏览是我之前没学会的知识,这次也学会了,完成的还是很不错的(自我感觉)
6. 图片下载网上找了很多办法，但都挺复杂的，要权限申请然后各种判断(可能跟文件保存有关)，我选了一个相对简单的方法完成的，就是通过glide下载直接存到相册里
7. 最后内存泄漏优化我还不太明白，等我搞明白了在做吧

### 个人感想：

- 我算是0基础吗？（不知道，反正只有学校接触过一点Android相关的实训，然后想往这个方向发展，就去看了《第一行代码》，不过我只看到一半，就来上这个课了，有点后悔没早点开始看，就来上培训班了）我是物联网工程专业的，这专业软件硬件都学，但都学的不深，我觉得要和哪些软工的比起来我是相当劣势的（几乎没有软件开发经验，而且平常接触c语言和硬件更多），所以我给我自己的目标是不管怎样坚持下来，这是一个相当好的机会，类似于提前体验工作，还是在小米这样的世界500强企业。我确实坚持下来了，不知道支撑我的是什么（家庭，信念，目标，向往？），反正坚持下来了是可以为我自己鼓掌的。

- 来之前我还是很有自信的，不知道为什么，可能是天生的乐观派吧。进营后，高强度学习了几天，我都还能接受。毕竟前面看过书，跟着老师就也能做出来。可是后面的课程越来越难了，我也没有了解，所以跟的比较吃力。我这时候意识到自己知识和能力的浅薄，我要走的路还很长，学习永无止境。老师的课当然讲的很好，但对我来说在短时间理解有点困难，我不得不在网上不断搜寻学习，直到我能够理解。我不太喜欢直接复制代码（如果不理解，就算完成作业也对我提升不大，结果（offer）固然重要，但过程（自己的学习积累）我觉得更重要），所以作业有时候会拖得很晚，这有点对不起老师了。而且我的学习比较孤独，我的同学们来的很少，来的人和我的关系也一般，我没有同学和我互相帮助，这可能也是我学习效率低下的原因之一。我其实很难去问别人问题，主要是怕有些问题很低级，那是在浪费别人时间，所以除非我真的解决不了，我不会寻求帮助的。但这不一定是对的，因为如果长时间卡在某一处，也是对自己时间的浪费，不如早点解决，这是我通过这次实践学到的。这次实践，我提高发现问题和解决问题的能力。因为我写代码的习惯很差，经常出了问题无法解决，一开始看到闪退毫无头绪，就在那盯着程序嗯看。后来学会了看报错信息，从结果反推原因并去解决问题。当问题解决时，快乐的产生是发自内心的。我学会了很多工具的使用，包括git、MD语言，还有各种库的使用，这让我受益良多。

- 关于最后三天的实践训练：这是我第一次单人完成一个项目的开发，其中遇到了数不尽的困难，不过我都一 一解决了，这让我信心大增。付出时间和努力是能做到力所能及的任何事的。不过还是有很多地方是值得优化的，从代码到各个方面，我会不断优化知道令我满意的。

- 感谢小米的每一位老师，包括上课的老师和带我的老师，和优秀的人在一起工作学习是一件很难得的事！

  最后也感谢我自己，没有我的坚持和努力，我是走不到最后的！#   c a o k u n _ w e i b o  
 #   w e i _ b o  
 