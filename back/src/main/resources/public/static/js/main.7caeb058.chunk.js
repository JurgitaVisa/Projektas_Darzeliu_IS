(this.webpackJsonpfront=this.webpackJsonpfront||[]).push([[0],{20:function(e,a,t){},32:function(e,a,t){e.exports=t(62)},40:function(e,a,t){},62:function(e,a,t){"use strict";t.r(a);var n=t(0),r=t.n(n),o=t(30),s=t.n(o),l=t(7),i=function(e){e&&e instanceof Function&&t.e(3).then(t.bind(null,63)).then((function(a){var t=a.getCLS,n=a.getFID,r=a.getFCP,o=a.getLCP,s=a.getTTFB;t(e),n(e),r(e),o(e),s(e)}))},c=(t(37),t(38),t(20),t(1)),u=(t(40),t(10)),m=t(11),p=t(13),d=t(12),g=t(8),v=t.n(g);v.a.interceptors.response.use(null,(function(e){return e.response&&e.response.status>=400&&e.response.status<500||(console.log("Logging unexpected error",e),alert("\u012evyko klaida, puslapis nurodytu adresu nepasiekiamas")),Promise.reject(e)}));var h={get:v.a.get,post:v.a.post,put:v.a.put,delete:v.a.delete},E=function(e){var a=e.username,t=e.password,n=e.loginError,o=e.onPassChange,s=e.onUsernameChange,l=e.onSubmit;return r.a.createElement("div",{className:"row d-flex justify-content-center"},r.a.createElement("form",{onSubmit:l},r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"username"},"Naudotojo vardas"),r.a.createElement("input",{type:"text",className:"form-control",id:"username",value:a,onChange:s})),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"password"},"Slapta\u017eodis"),r.a.createElement("input",{type:"password",className:"form-control",id:"password",value:t,onChange:o})),r.a.createElement("button",{type:"submit",className:"btn btn-primary"},"Prisijungti")),n&&r.a.createElement("div",{className:"alert alert-danger",role:"alert"},"Neteisingi prisijungimo duomenys!"))};v.a.defaults.withCredentials=!0;var b=function(e){Object(p.a)(t,e);var a=Object(d.a)(t);function t(e){var n;return Object(u.a)(this,t),(n=a.call(this,e)).handleUsernameChange=function(e){n.setState({username:e.target.value})},n.handlePassChange=function(e){n.setState({password:e.target.value})},n.handleSubmit=function(e){var a=new URLSearchParams;a.append("username",n.state.username),a.append("password",n.state.password),h.post("".concat("http://localhost:8080","/login"),a,{headers:{"Content-type":"application/x-www-form-urlencoded"}}).then((function(e){console.log("user "+e.data.username+" logged in"),n.props.history.push("/darzelis/home")})).catch((function(e){console.log("Error log from Login submit",e),401===e.response.status&&n.setState({loginError:!0})})),e.preventDefault()},n.state={username:"",password:"",loginError:!1},n}return Object(m.a)(t,[{key:"render",value:function(){return r.a.createElement("div",null,r.a.createElement("div",{className:"text-center"},r.a.createElement("h5",null,"Sveiki atvyk\u0119! "),r.a.createElement("p",null,"Login i\u0161 Andriaus skaidri\u0173:")),r.a.createElement(E,{username:this.state.username,password:this.state.password,loginError:this.state.loginError,onUsernameChange:this.handleUsernameChange,onPassChange:this.handlePassChange,onSubmit:this.handleSubmit}))}}]),t}(n.Component);function f(e){var a=e.onLogout;return r.a.createElement("div",null,r.a.createElement("button",{onClick:a,className:"btn btn-outline-primary m-1"},"Atsijungti"))}function k(){var e=Object(c.f)();return r.a.createElement("div",null,r.a.createElement(f,{onLogout:function(a){h.post("".concat("http://localhost:8080","/logout")).then((function(a){e.push("/")})).catch((function(e){console.log("Error on logout",e)}))}}))}var j=function(){return r.a.createElement("div",{className:"pt-3"},r.a.createElement("nav",{className:"navbar navbar-expand-md navbar-light bg-light"},r.a.createElement(l.c,{className:"navbar-brand",to:"/darzelis/home"},"Logo(home)"),r.a.createElement("button",{className:"navbar-toggler",type:"button","data-toggle":"collapse","data-target":"#navbarSupportedContent","aria-controls":"navbarSupportedContent","aria-expanded":"false","aria-label":"Toggle navigation"},r.a.createElement("span",{className:"navbar-toggler-icon"})),r.a.createElement("div",{className:"collapse navbar-collapse",id:"navbarSupportedContent"},r.a.createElement("ul",{className:"navbar-nav mr-auto"},r.a.createElement("li",{className:"nav-item p-1"},r.a.createElement(l.c,{className:"nav-link",to:"/darzelis/admin"},"Naudotoj\u0173 administravimas")),r.a.createElement(k,null)))))},y=function(e){Object(p.a)(t,e);var a=Object(d.a)(t);function t(){return Object(u.a)(this,t),a.apply(this,arguments)}return Object(m.a)(t,[{key:"render",value:function(){return r.a.createElement("div",null,r.a.createElement(j,null),r.a.createElement("h5",null,"Sveiki prisijung\u0119 prie dar\u017eeli\u0173 IS"),r.a.createElement("p",null,"Atsisi\u0173sti naudotojo instrukcij\u0105"))}}]),t}(n.Component),N=function(){var e=Object(c.g)();return r.a.createElement("div",null,r.a.createElement("p",{className:"ml-2"},"Puslapis adresu: ",e.pathname," nerastas"),r.a.createElement(l.b,{to:"/darzelis/home",className:"btn btn-primary ml-2"},"Pradinis"))},C=function(e){Object(p.a)(t,e);var a=Object(d.a)(t);function t(){return Object(u.a)(this,t),a.apply(this,arguments)}return Object(m.a)(t,[{key:"render",value:function(){return r.a.createElement("div",null,r.a.createElement(j,null),r.a.createElement("h5",null,"Administratoriaus langas (\u010dia bus naudotoj\u0173 s\u0105ra\u0161as ir naujo naudotojo \u012fvedimo mygtukas)"),r.a.createElement("p",null,'Servisus si\u016blau kviesti per "httpService" ir "apiEndpoint" (u\u017ekomentuotas pavyzdys \u012fd\u0117tas Admin komponente), kad gal\u0117tume bet kada pakeisti axios arba url adres\u0105, jei reiks. '),r.a.createElement("p",null,"Pavadinimai gali b\u016bti kei\u010diami, kad visiems b\u016bt\u0173 ai\u0161k\u016bs. A\u0161 juos i\u0161 Mosh tutorial pa\u0117miau, kai mokiausi; v\u0117liau adresus reiks sutvarkyti ir papildyti. "))}}]),t}(n.Component);var S=function(){return r.a.createElement("div",{className:"container pt-5"},r.a.createElement(c.c,null,r.a.createElement(c.a,{exact:!0,path:"/",component:b}),r.a.createElement(c.a,{path:"/darzelis/home",component:y}),r.a.createElement(c.a,{path:"/darzelis/admin",component:C}),r.a.createElement(c.a,{path:"*",component:N}),r.a.createElement(c.a,{component:N})))};document.title="Never Latte",s.a.render(r.a.createElement(r.a.StrictMode,null,r.a.createElement(l.a,{basename:"."},r.a.createElement(S,null))),document.getElementById("root")),i()}},[[32,1,2]]]);
//# sourceMappingURL=main.7caeb058.chunk.js.map