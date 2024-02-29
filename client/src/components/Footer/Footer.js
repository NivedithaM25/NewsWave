import React from "react";
import "./Footer.css";
import Logo from "./newswave-logo.jpg";
const Footer = () => {
  const year = new Date().getFullYear();
  return (
    <div>
      <div className="footer_container">
        <div className="footleft">
          <img className="NW_biglogo" src={Logo} alt="News Wave" />
          <br />
          <table className="footer_tableMain">
            <tbody>
              <tr>
                <td>About Us</td>
                <td>Newsletter</td>
              </tr>
              <tr>
                <td>Create Your Own Ad</td>
                <td>Feedback</td>
              </tr>
              <tr>
                <td>Terms of Use</td>
                <td>ePaper</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="footmid">
          <h5 className="footer_heading">POPULAR CATEGORIES</h5>
          <table className="footer_tableMain">
            <tbody>
              <tr>
                <td>Entertainment</td>
                <td>General News</td>
              </tr>
              <tr>
                <td>Sports News</td>
                <td>Health and Fitness</td>
              </tr>
              <tr>
                <td>Business News</td>
                <td>Science</td>
              </tr>
              <tr>
                <td>Technology</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div className="footright">
          <h5 className="footer_heading">COUNTRIES</h5>
          <table className="footer_tableMain">
            <tbody>
              <tr>
                <td>United Arab Emirates(ae)</td>
                <td>Argentina(ar)</td>
                <td>Austria(at)</td>
                <td>Belgium(be)</td>
                <td>Bulgaria(bg)</td>
              </tr>
              <tr>
                <td>Brazil(br)</td>
                <td>Canada(ca)</td>
                <td>Switzerland(ch)</td>
                <td>China(cn)</td>
                <td>Colombia(co)</td>
              </tr>
              <tr>
                <td>Cuba(cu)</td>
                <td>Czech Republic(cz)</td>
                <td>Germany(de)</td>
                <td>Egypt(eg)</td>
                <td>France(fr)</td>
              </tr>
              <tr>
                <td>United Kingdom (Great Britain)(gb)</td>
                <td>Greece(gr)</td>
                <td>Hong Kong(hk)</td>
                <td>Hungary(hu)</td>
                <td>Indonesia(id)</td>
              </tr>
              <tr>
                <td>Ireland(ie)</td>
                <td>Israel(il)</td>
                <td>India(in)</td>
                <td>Italy(it)</td>
                <td>Japan(jp)</td>
              </tr>
              <tr>
                <td>South Korea (Republic of Korea)(kr)</td>
                <td>Lithuania(lt)</td>
                <td>Latvia(lv)</td>
                <td>Morocco(ma)</td>
                <td>Mexico(mx)</td>
              </tr>
              <tr>
                <td>Malaysia(my)</td>
                <td>Nigeria(ng)</td>
                <td>Netherlands(nl)</td>
                <td>Norway(no)</td>
                <td>New Zealand(nz)</td>
              </tr>
              <tr>
                <td>Philippines(ph)</td>
                <td>Poland(pl)</td>
                <td>Portugal(pt)</td>
                <td>Romania(ro)</td>
                <td>Serbia(rs)</td>
              </tr>
              <tr>
                <td>Russia(rs)</td>
                <td>Saudi Arabia(sa)</td>
                <td>Sweden(se)</td>
                <td>Singapore(sg)</td>
                <td>Slovenia(si)</td>
              </tr>
              <tr>
                <td>Slovakia(sk)</td>
                <td>Thailand(th)</td>
                <td>Turkey(tr)</td>
                <td>Taiwan(tw)</td>
                <td>Ukraine(ua)</td>
              </tr>
              <tr>
                <td> United States of America(us)</td>
                <td>Venezuela(ve)</td>
                <td> South Africa(za)</td>
                <td></td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div className="footer_copyRight">
        <p>Copyright &copy; {year} Made with ❤ by Niveditha Muthukuru</p>
      </div>
    </div>
  );
};

export default Footer;
