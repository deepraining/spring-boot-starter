# 说明

**1** 发送`简单模式队列`消息，在浏览器执行

```
fetch('/sendSimple').then(res=>res.json()).then(console.log)
```

**2** 发送`抢占模式队列`消息，在浏览器执行

```
fetch('/sendRace').then(res=>res.json()).then(console.log)
```

**3** 发送`订阅模式队列`消息，在浏览器执行

```
fetch('/sendSubscribe').then(res=>res.json()).then(console.log)
```

**4** 发送`路由模式队列`消息，在浏览器执行

```
fetch('/sendRoute1').then(res=>res.json()).then(console.log)
fetch('/sendRoute11').then(res=>res.json()).then(console.log)
fetch('/sendRoute2').then(res=>res.json()).then(console.log)
fetch('/sendRoute22').then(res=>res.json()).then(console.log)
```

**5** 发送`主题模式队列`消息，在浏览器执行

```
fetch('/sendTopic1').then(res=>res.json()).then(console.log)
fetch('/sendTopic2').then(res=>res.json()).then(console.log)
fetch('/sendTopic3').then(res=>res.json()).then(console.log)
```
