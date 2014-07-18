<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
    <%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

    <html>
      <body>
        <form action="" method="post">
        <%
          ReCaptcha c = ReCaptchaFactory.newReCaptcha("your_public_key", "your_private_key", false);
          out.print(c.createRecaptchaHtml(null, null));
        %>
        <input type="submit" value="submit" />
        </form>
      </body>
    </html>