<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Modal -->
<div class="modal fade" id="groupModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف گروه محصول</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>کد گروه :</h4>
                                    <input name="gc" type="text" class="form-control"/>
                                </div>
                                <div class="col-md-4 col-md-pull-3">
                                    <h4>نام گروه :</h4>
                                    <input name="gn" type="text" class="form-control"/>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel-group" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" class="collapsed">دستگاه ها</a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                    <div class="panel-body">
                                        <table id="machineTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>کد</th>
                                                <th>نام</th>
                                                <th>جزییات</th>
                                            </tr>
                                            </thead>
                                            <tbody id="machineBody">
                                            </tbody>
                                        </table>
                                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#machineModal" onclick="newModal('machineModal')"><i class="fa fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">تولیدات دستگاه ها</a>
                                    </h4>
                                </div>
                                <div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                    <div class="panel-body">
                                        <table id="productTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>کد محصول</th>
                                                <th>نام محصول</th>
                                                <th>جزییات</th>
                                            </tr>
                                            </thead>
                                            <tbody id="productBody">
                                            </tbody>
                                        </table>
                                        <button id="productButton" class="btn btn-outline btn-primary" data-toggle="modal" href="#productModal" onclick="newModal('productModal')"><i class="fa fa-plus"></i>محصول نهایی</button>
                                        <button id="semiButton" class="btn btn-outline btn-primary" data-toggle="modal" href="#semistructuredModal" onclick="newModal('semistructuredModal')"><i class="fa fa-plus"></i>خروجی ایستگاه</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col-md-12 -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptGroupModal()">تایید</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<div class="modal fade" id="machineModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف دستگاه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <legend>مشخصات کلی</legend>
                            <div class="row">
                                <div class="col-md-4">
                                    <p> کد دستگاه : </p>
                                    <input type="text" name="mc" placeholder="کد دستگاه"  class="form-control">
                                </div>
                                <div class="col-md-4 col-md-pull-3">
                                    <p> نام دستگاه : </p>
                                    <input type="text" name="mn" placeholder="نام دستگاه"  class="form-control">
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <legend>جزییات</legend>
                            <div class="row">
                                <div class="col-md-3">
                                    <p> ظرفیت دستگاه : </p>
                                    <input step="any" type="number" name="capacity" placeholder="ظرفیت دستگاه"  class="form-control">
                                </div>
                                <div class="col-md-3">
                                    <p> مقدار تولید در ساعت : </p>
                                    <input step="any" type="number" name="uph" placeholder="مقدار تولید در ساعت"  class="form-control">
                                </div>
                                <div class="col-md-3">
                                    <p>تاریخ تولید شده : </p>
                                    <input id="datepicker" type="text" name="d" placeholder="تاریخ تولید شده" class="form-control">
                                </div>
                                <div class="col-md-3">
                                    <p>کشور تولید شده : </p>
                                    <select name="country" class="form-control" >
                                        <option value="AFG" selected>Afghanistan</option>
                                        <option value="ALA">Åland Islands</option>
                                        <option value="ALB">Albania</option>
                                        <option value="DZA">Algeria</option>
                                        <option value="ASM">American Samoa</option>
                                        <option value="AND">Andorra</option>
                                        <option value="AGO">Angola</option>
                                        <option value="AIA">Anguilla</option>
                                        <option value="ATA">Antarctica</option>
                                        <option value="ATG">Antigua and Barbuda</option>
                                        <option value="ARG">Argentina</option>
                                        <option value="ARM">Armenia</option>
                                        <option value="ABW">Aruba</option>
                                        <option value="AUS">Australia</option>
                                        <option value="AUT">Austria</option>
                                        <option value="AZE">Azerbaijan</option>
                                        <option value="BHS">Bahamas</option>
                                        <option value="BHR">Bahrain</option>
                                        <option value="BGD">Bangladesh</option>
                                        <option value="BRB">Barbados</option>
                                        <option value="BLR">Belarus</option>
                                        <option value="BEL">Belgium</option>
                                        <option value="BLZ">Belize</option>
                                        <option value="BEN">Benin</option>
                                        <option value="BMU">Bermuda</option>
                                        <option value="BTN">Bhutan</option>
                                        <option value="BOL">Bolivia, Plurinational State of</option>
                                        <option value="BES">Bonaire, Sint Eustatius and Saba</option>
                                        <option value="BIH">Bosnia and Herzegovina</option>
                                        <option value="BWA">Botswana</option>
                                        <option value="BVT">Bouvet Island</option>
                                        <option value="BRA">Brazil</option>
                                        <option value="IOT">British Indian Ocean Territory</option>
                                        <option value="BRN">Brunei Darussalam</option>
                                        <option value="BGR">Bulgaria</option>
                                        <option value="BFA">Burkina Faso</option>
                                        <option value="BDI">Burundi</option>
                                        <option value="KHM">Cambodia</option>
                                        <option value="CMR">Cameroon</option>
                                        <option value="CAN">Canada</option>
                                        <option value="CPV">Cape Verde</option>
                                        <option value="CYM">Cayman Islands</option>
                                        <option value="CAF">Central African Republic</option>
                                        <option value="TCD">Chad</option>
                                        <option value="CHL">Chile</option>
                                        <option value="CHN">China</option>
                                        <option value="CXR">Christmas Island</option>
                                        <option value="CCK">Cocos (Keeling) Islands</option>
                                        <option value="COL">Colombia</option>
                                        <option value="COM">Comoros</option>
                                        <option value="COG">Congo</option>
                                        <option value="COD">Congo, the Democratic Republic of the</option>
                                        <option value="COK">Cook Islands</option>
                                        <option value="CRI">Costa Rica</option>
                                        <option value="CIV">Côte d'Ivoire</option>
                                        <option value="HRV">Croatia</option>
                                        <option value="CUB">Cuba</option>
                                        <option value="CUW">Curaçao</option>
                                        <option value="CYP">Cyprus</option>
                                        <option value="CZE">Czech Republic</option>
                                        <option value="DNK">Denmark</option>
                                        <option value="DJI">Djibouti</option>
                                        <option value="DMA">Dominica</option>
                                        <option value="DOM">Dominican Republic</option>
                                        <option value="ECU">Ecuador</option>
                                        <option value="EGY">Egypt</option>
                                        <option value="SLV">El Salvador</option>
                                        <option value="GNQ">Equatorial Guinea</option>
                                        <option value="ERI">Eritrea</option>
                                        <option value="EST">Estonia</option>
                                        <option value="ETH">Ethiopia</option>
                                        <option value="FLK">Falkland Islands (Malvinas)</option>
                                        <option value="FRO">Faroe Islands</option>
                                        <option value="FJI">Fiji</option>
                                        <option value="FIN">Finland</option>
                                        <option value="FRA">France</option>
                                        <option value="GUF">French Guiana</option>
                                        <option value="PYF">French Polynesia</option>
                                        <option value="ATF">French Southern Territories</option>
                                        <option value="GAB">Gabon</option>
                                        <option value="GMB">Gambia</option>
                                        <option value="GEO">Georgia</option>
                                        <option value="DEU">Germany</option>
                                        <option value="GHA">Ghana</option>
                                        <option value="GIB">Gibraltar</option>
                                        <option value="GRC">Greece</option>
                                        <option value="GRL">Greenland</option>
                                        <option value="GRD">Grenada</option>
                                        <option value="GLP">Guadeloupe</option>
                                        <option value="GUM">Guam</option>
                                        <option value="GTM">Guatemala</option>
                                        <option value="GGY">Guernsey</option>
                                        <option value="GIN">Guinea</option>
                                        <option value="GNB">Guinea-Bissau</option>
                                        <option value="GUY">Guyana</option>
                                        <option value="HTI">Haiti</option>
                                        <option value="HMD">Heard Island and McDonald Islands</option>
                                        <option value="VAT">Holy See (Vatican City State)</option>
                                        <option value="HND">Honduras</option>
                                        <option value="HKG">Hong Kong</option>
                                        <option value="HUN">Hungary</option>
                                        <option value="ISL">Iceland</option>
                                        <option value="IND">India</option>
                                        <option value="IDN">Indonesia</option>
                                        <option value="IRN">Iran, Islamic Republic of</option>
                                        <option value="IRQ">Iraq</option>
                                        <option value="IRL">Ireland</option>
                                        <option value="IMN">Isle of Man</option>
                                        <option value="ISR">Israel</option>
                                        <option value="ITA">Italy</option>
                                        <option value="JAM">Jamaica</option>
                                        <option value="JPN">Japan</option>
                                        <option value="JEY">Jersey</option>
                                        <option value="JOR">Jordan</option>
                                        <option value="KAZ">Kazakhstan</option>
                                        <option value="KEN">Kenya</option>
                                        <option value="KIR">Kiribati</option>
                                        <option value="PRK">Korea, Democratic People's Republic of</option>
                                        <option value="KOR">Korea, Republic of</option>
                                        <option value="KWT">Kuwait</option>
                                        <option value="KGZ">Kyrgyzstan</option>
                                        <option value="LAO">Lao People's Democratic Republic</option>
                                        <option value="LVA">Latvia</option>
                                        <option value="LBN">Lebanon</option>
                                        <option value="LSO">Lesotho</option>
                                        <option value="LBR">Liberia</option>
                                        <option value="LBY">Libya</option>
                                        <option value="LIE">Liechtenstein</option>
                                        <option value="LTU">Lithuania</option>
                                        <option value="LUX">Luxembourg</option>
                                        <option value="MAC">Macao</option>
                                        <option value="MKD">Macedonia, the former Yugoslav Republic of</option>
                                        <option value="MDG">Madagascar</option>
                                        <option value="MWI">Malawi</option>
                                        <option value="MYS">Malaysia</option>
                                        <option value="MDV">Maldives</option>
                                        <option value="MLI">Mali</option>
                                        <option value="MLT">Malta</option>
                                        <option value="MHL">Marshall Islands</option>
                                        <option value="MTQ">Martinique</option>
                                        <option value="MRT">Mauritania</option>
                                        <option value="MUS">Mauritius</option>
                                        <option value="MYT">Mayotte</option>
                                        <option value="MEX">Mexico</option>
                                        <option value="FSM">Micronesia, Federated States of</option>
                                        <option value="MDA">Moldova, Republic of</option>
                                        <option value="MCO">Monaco</option>
                                        <option value="MNG">Mongolia</option>
                                        <option value="MNE">Montenegro</option>
                                        <option value="MSR">Montserrat</option>
                                        <option value="MAR">Morocco</option>
                                        <option value="MOZ">Mozambique</option>
                                        <option value="MMR">Myanmar</option>
                                        <option value="NAM">Namibia</option>
                                        <option value="NRU">Nauru</option>
                                        <option value="NPL">Nepal</option>
                                        <option value="NLD">Netherlands</option>
                                        <option value="NCL">New Caledonia</option>
                                        <option value="NZL">New Zealand</option>
                                        <option value="NIC">Nicaragua</option>
                                        <option value="NER">Niger</option>
                                        <option value="NGA">Nigeria</option>
                                        <option value="NIU">Niue</option>
                                        <option value="NFK">Norfolk Island</option>
                                        <option value="MNP">Northern Mariana Islands</option>
                                        <option value="NOR">Norway</option>
                                        <option value="OMN">Oman</option>
                                        <option value="PAK">Pakistan</option>
                                        <option value="PLW">Palau</option>
                                        <option value="PSE">Palestinian Territory, Occupied</option>
                                        <option value="PAN">Panama</option>
                                        <option value="PNG">Papua New Guinea</option>
                                        <option value="PRY">Paraguay</option>
                                        <option value="PER">Peru</option>
                                        <option value="PHL">Philippines</option>
                                        <option value="PCN">Pitcairn</option>
                                        <option value="POL">Poland</option>
                                        <option value="PRT">Portugal</option>
                                        <option value="PRI">Puerto Rico</option>
                                        <option value="QAT">Qatar</option>
                                        <option value="REU">Réunion</option>
                                        <option value="ROU">Romania</option>
                                        <option value="RUS">Russian Federation</option>
                                        <option value="RWA">Rwanda</option>
                                        <option value="BLM">Saint Barthélemy</option>
                                        <option value="SHN">Saint Helena, Ascension and Tristan da Cunha</option>
                                        <option value="KNA">Saint Kitts and Nevis</option>
                                        <option value="LCA">Saint Lucia</option>
                                        <option value="MAF">Saint Martin (French part)</option>
                                        <option value="SPM">Saint Pierre and Miquelon</option>
                                        <option value="VCT">Saint Vincent and the Grenadines</option>
                                        <option value="WSM">Samoa</option>
                                        <option value="SMR">San Marino</option>
                                        <option value="STP">Sao Tome and Principe</option>
                                        <option value="SAU">Saudi Arabia</option>
                                        <option value="SEN">Senegal</option>
                                        <option value="SRB">Serbia</option>
                                        <option value="SYC">Seychelles</option>
                                        <option value="SLE">Sierra Leone</option>
                                        <option value="SGP">Singapore</option>
                                        <option value="SXM">Sint Maarten (Dutch part)</option>
                                        <option value="SVK">Slovakia</option>
                                        <option value="SVN">Slovenia</option>
                                        <option value="SLB">Solomon Islands</option>
                                        <option value="SOM">Somalia</option>
                                        <option value="ZAF">South Africa</option>
                                        <option value="SGS">South Georgia and the South Sandwich Islands</option>
                                        <option value="SSD">South Sudan</option>
                                        <option value="ESP">Spain</option>
                                        <option value="LKA">Sri Lanka</option>
                                        <option value="SDN">Sudan</option>
                                        <option value="SUR">Suriname</option>
                                        <option value="SJM">Svalbard and Jan Mayen</option>
                                        <option value="SWZ">Swaziland</option>
                                        <option value="SWE">Sweden</option>
                                        <option value="CHE">Switzerland</option>
                                        <option value="SYR">Syrian Arab Republic</option>
                                        <option value="TWN">Taiwan, Province of China</option>
                                        <option value="TJK">Tajikistan</option>
                                        <option value="TZA">Tanzania, United Republic of</option>
                                        <option value="THA">Thailand</option>
                                        <option value="TLS">Timor-Leste</option>
                                        <option value="TGO">Togo</option>
                                        <option value="TKL">Tokelau</option>
                                        <option value="TON">Tonga</option>
                                        <option value="TTO">Trinidad and Tobago</option>
                                        <option value="TUN">Tunisia</option>
                                        <option value="TUR">Turkey</option>
                                        <option value="TKM">Turkmenistan</option>
                                        <option value="TCA">Turks and Caicos Islands</option>
                                        <option value="TUV">Tuvalu</option>
                                        <option value="UGA">Uganda</option>
                                        <option value="UKR">Ukraine</option>
                                        <option value="ARE">United Arab Emirates</option>
                                        <option value="GBR">United Kingdom</option>
                                        <option value="USA">United States</option>
                                        <option value="UMI">United States Minor Outlying Islands</option>
                                        <option value="URY">Uruguay</option>
                                        <option value="UZB">Uzbekistan</option>
                                        <option value="VUT">Vanuatu</option>
                                        <option value="VEN">Venezuela, Bolivarian Republic of</option>
                                        <option value="VNM">Viet Nam</option>
                                        <option value="VGB">Virgin Islands, British</option>
                                        <option value="VIR">Virgin Islands, U.S.</option>
                                        <option value="WLF">Wallis and Futuna</option>
                                        <option value="ESH">Western Sahara</option>
                                        <option value="YEM">Yemen</option>
                                        <option value="ZMB">Zambia</option>
                                        <option value="ZWE">Zimbabwe</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p> توضیحات : </p>
                                    <textarea name="des" class="form-control" maxlength="255"></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <fieldset name="udf">
                            <legend>مشخصات اضافی</legend>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-default" onclick="addFieldSectionRegister(this)">فیلد جدید</button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptMachineModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="productModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف محصولات نهایی</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <legend>مشخصات محصول</legend>
                            <div class="row">
                                <div class="col-md-3">
                                    <p> کد محصول : </p>
                                    <input type="text" name="pc" class="form-control"/>
                                </div>
                                <div class="col-md-3">
                                    <p> سایز محصول : </p>
                                    <input type="text" name="ps" class="form-control"/>
                                </div>
                                <div class="col-md-3">
                                    <p> طرح محصول : </p>
                                    <input type="text" name="pd" class="form-control"/>
                                </div>
                                <div class="col-md-3">
                                    <p> pr محصول : </p>
                                    <input type="text" name="pr" class="form-control"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <p> وزن محصول : </p>
                                    <input type="number" name="pw" class="form-control"/>
                                </div>
                                <div class="col-md-9">
                                    <p> توضیحات : </p>
                                    <textarea name="des" class="form-control" maxlength="255"></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <fieldset name="udf">
                            <legend>مشخصات اضافی</legend>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-default" onclick="addFieldSectionRegister(this)">فیلد جدید</button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptProductModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="semistructuredModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف خروجی دستگاه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <legend>مشخصات خروجی</legend>
                            <div class="row">
                                <div class="col-md-4">
                                    <p> کد خروجی : </p>
                                    <input type="text" name="ec" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <p> نام خروجی : </p>
                                    <input type="text" name="en" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <p> واحد خروجی : </p>
                                    <input type="text" name="eu" class="form-control" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <p> حداقل age : </p>
                                    <input type="number" name="eamin"  class="form-control"/>
                                </div>
                                <div class="col-md-3">
                                    <p> حداکثر age : </p>
                                    <input type="number" name="eamax" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <p> حداقل مورد نیاز در انبار : </p>
                                    <input type="number" name="enmin" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <p> حداکثر مورد نیاز در انبار : </p>
                                    <input type="number" name="enmax" class="form-control" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p> توضیحات : </p>
                                    <textarea name="des" class="form-control" maxlength="255"></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <fieldset name="udf">
                            <legend>مشخصات اضافی</legend>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-default" onclick="addFieldSectionRegister(this)">فیلد جدید</button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptSemiModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<div class="row">
    <div class="col-md-12">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h2>تعریف قسمت ها</h2>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-4">
                                <h3>کد قسمت :</h3>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <input name="sc" type="text" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <h3>نام قسمت :</h3>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <input name="sn" type="text" class="form-control"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h2>تعریف گروه ها</h2>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <table id="groupTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th>کد</th>
                                        <th>نام</th>
                                        <th>جزییات</th>
                                    </tr>
                                    </thead>
                                    <tbody id="groupBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-md-4">
                                <button type="button" class="btn btn-outline btn-primary" data-toggle="modal" href="#groupModal" onclick="newModal('groupModal')"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-md-offset-8">

                            </div>
                            <div class="col-md-4">
                                <div class="col-md-offset-4">

                                </div>
                                <div class="col-md-4">
                                    <button type="reset" class="btn btn-danger">لغو</button>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-success" onclick="acceptSection()">تایید</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#groupTable").DataTable();
        $("#machineTable").DataTable();
        $("#datepicker").datepicker({
            todayBtn: true,
            clearBtn: true,
            format: "mm-dd-yyyy",
            todayHighlight:true,
            toggleActive: true
        });
    });
    var section = new Object();
    var groups = new Array();
    var machines = new Array();
    var products = new Array();
    var semistructureds = new Array();

    function acceptGroupModal() {
        if ($("#groupModal").data("data") != null){
            var group = groups[groups.indexOf($("#groupModal").data("data"))];
            group.code = $("#groupModal").find('input[name="gc"]').val();
            group.name = $("#groupModal").find('input[name="gn"]').val();
            group.machines = machines;
            group.products = products;
            group.semistructureds = semistructureds;
        }else {
            var group = new Object();
            group.code = $("#groupModal").find('input[name="gc"]').val();
            group.name = $("#groupModal").find('input[name="gn"]').val();
            group.machines = machines;
            group.products = products;
            group.semistructureds = semistructureds;
            groups.push(group);
        }
        groupTable();
        removeModal();
    }
    function acceptMachineModal() {
        if ($("#machineModal").data("data") != null){
            var machine = machines[machines.indexOf($("#machineModal").data("data"))];
            machine.code = $("#machineModal").find('input[name="mc"]').val();
            machine.name = $("#machineModal").find('input[name="mn"]').val();
            machine.capacity = $("#machineModal").find('input[name="capacity"]').val();
            machine.uph = $("#machineModal").find('input[name="uph"]').val();
            machine.d = $("#machineModal").find('input[name="d"]').val();
            machine.country = $("#machineModal").find('select[name="country"]').val();
            machine.des = $("#machineModal").find('textarea[name="des"]').val();
            if ($("#machineModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#machineModal").find('input[name="fn"]');
                var types = $("#machineModal").find('select[name="ft"]');
                var values = $("#machineModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                machine.udf = udfs;
            }
        }else {
            var machine = new Object();
            machine.code = $("#machineModal").find('input[name="mc"]').val();
            machine.name = $("#machineModal").find('input[name="mn"]').val();
            machine.capacity = $("#machineModal").find('input[name="capacity"]').val();
            machine.uph = $("#machineModal").find('input[name="uph"]').val();
            machine.d = $("#machineModal").find('input[name="d"]').val();
            machine.country = $("#machineModal").find('select[name="country"]').val();
            machine.des = $("#machineModal").find('textarea[name="des"]').val();
            if ($("#machineModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#machineModal").find('input[name="fn"]');
                var types = $("#machineModal").find('select[name="ft"]');
                var values = $("#machineModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                machine.udf = udfs;
            }
            machines.push(machine);
        }
        machineTable();
        removeModal();
    }
    function acceptProductModal() {
        if ($("#productModal").data("data") != null){
            var product = products[products.indexOf($("#productModal").data("data"))];
            product.code = $("#productModal").find('input[name="pc"]').val();
            product.size = $("#productModal").find('input[name="ps"]').val();
            product.design = $("#productModal").find('input[name="pd"]').val();
            product.pr = $("#productModal").find('input[name="pr"]').val();
            product.weight = $("#productModal").find('input[name="pw"]').val();
            product.des = $("#productModal").find('textarea[name="des"]').val();
            if ($("#productModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#productModal").find('input[name="fn"]');
                var types = $("#productModal").find('select[name="ft"]');
                var values = $("#productModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                product.udf = udfs;
            }
        }else {
            var product = new Object();
            product.code = $("#productModal").find('input[name="pc"]').val();
            product.size = $("#productModal").find('input[name="ps"]').val();
            product.design = $("#productModal").find('input[name="pd"]').val();
            product.pr = $("#productModal").find('input[name="pr"]').val();
            product.weight = $("#productModal").find('input[name="pw"]').val();
            product.des = $("#productModal").find('textarea[name="des"]').val();
            if ($("#productModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#productModal").find('input[name="fn"]');
                var types = $("#productModal").find('select[name="ft"]');
                var values = $("#productModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                product.udf = udfs;
            }
            products.push(product);
        }
        productTable();
        checkProduct();
        removeModal();
    }
    function acceptSemiModal() {
        if ($("#semistructuredModal").data("data") != null){
            var semi = semistructureds[semistructureds.indexOf($("#semistructuredModal").data("data"))];
            semi.code = $("#semistructuredModal").find('input[name="ec"]').val();
            semi.name = $("#semistructuredModal").find('input[name="en"]').val();
            semi.unit = $("#semistructuredModal").find('input[name="eu"]').val();
            semi.ageMin = $("#semistructuredModal").find('input[name="eamin"]').val();
            semi.ageMax = $("#semistructuredModal").find('input[name="eamax"]').val();
            semi.needMin = $("#semistructuredModal").find('input[name="enmin"]').val();
            semi.needMax = $("#semistructuredModal").find('input[name="enmax"]').val();
            semi.des = $("#semistructuredModal").find('textarea[name="des"]').val();
            if ($("#semistructuredModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#semistructuredModal").find('input[name="fn"]');
                var types = $("#semistructuredModal").find('select[name="ft"]');
                var values = $("#semistructuredModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                semi.udf = udfs;
            }
        }else {
            var semi = new Object();
            semi.code = $("#semistructuredModal").find('input[name="ec"]').val();
            semi.name = $("#semistructuredModal").find('input[name="en"]').val();
            semi.unit = $("#semistructuredModal").find('input[name="eu"]').val();
            semi.ageMin = $("#semistructuredModal").find('input[name="eamin"]').val();
            semi.ageMax = $("#semistructuredModal").find('input[name="eamax"]').val();
            semi.needMin = $("#semistructuredModal").find('input[name="enmin"]').val();
            semi.needMax = $("#semistructuredModal").find('input[name="enmax"]').val();
            semi.des = $("#semistructuredModal").find('textarea[name="des"]').val();
            if ($("#semistructuredModal").find('input[name="fn"]') != null){
                var udfs = new Array();
                var names = $("#semistructuredModal").find('input[name="fn"]');
                var types = $("#semistructuredModal").find('select[name="ft"]');
                var values = $("#semistructuredModal").find('input[name="fv"]');
                for (var i = 0; i < names.length; i++){
                    var udf = new Object();
                    udf.name = $(names[i]).val();
                    udf.type = $(types[i]).val();
                    udf.value = $(values[i]).val();
                    udfs.push(udf);
                }
                semi.udf = udfs;
            }
            semistructureds.push(semi);
        }
        semiTable();
        checkProduct();
        removeModal();
    }
    function acceptSection() {
        section.code = $("#page-wrapper").find('input[name="sc"]').val();
        section.name = $("#page-wrapper").find('input[name="sn"]').val();
        section.groups = groups;
        $.ajax("/document/machine/register.do",{
            data:{data:JSON.stringify(section)},
            type:"POST"
        }).done(function (data) {
            loadContent("/document/sectionRegister.jsp", false);
            alertify.success("اطلاعات ثبت شد");
        }).fail(function (data) {
            errors(data);
        })
    }

    function groupTable(){
        $("#groupTable").DataTable().destroy();
        $("#groupBody").children().remove();
        for (var i = 0; i < groups.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", groups[i]);
            $(a).on("click", function () {
                deleteRowGroup($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(groups[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(groups[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", groups[i]);
            $(a).on("click", function (event) {
                var group = $(this).data("data");
                $("#groupModal").data("data", group);
                $("#groupModal").find('input[name="gc"]').val(group.code);
                $("#groupModal").find('input[name="gn"]').val(group.name);
                machines = group.machines;
                machineTable();
                if (group.products.length > 0){
                    products = group.products;
                    productTable();
                }else if (group.semistructureds.length > 0){
                    semistructureds = group.semistructureds;
                    semiTable();
                }else {
                    products = new Array();
                    semistructureds = new Array();
                }
                checkProduct();
                addModal("groupModal");
                $("#groupModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#groupBody").append(tr);
        }
        createDataTable($("#groupTable"));
    }
    function machineTable() {
        $("#machineTable").DataTable().destroy();
        $("#machineBody").children().remove();
        for (var i = 0; i < machines.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", machines[i]);
            $(a).on("click", function () {
                deleteRowMachine($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).attr("cursor", "pointer");
            $(a).data("data", machines[i]);
            $(a).on("click", function () {
                var machine = $(this).data("data");
                $("#machineModal").data("data", machine);
                $("#machineModal").find('input[name="mc"]').val(machine.code);
                $("#machineModal").find('input[name="mn"]').val(machine.name);
                $("#machineModal").find('input[name="capacity"]').val(machine.capacity);
                $("#machineModal").find('input[name="uph"]').val(machine.uph);
                $("#machineModal").find('input[name="d"]').val(machine.d);
                $("#machineModal").find('select[name="country"]').val(machine.country);
                $("#machineModal").find('textarea[name="des"]').val(machine.des);
                if (machine.udf != null){

                    $("#machineModal").find("fieldset[name='udf']").children().remove();
                    var legend = document.createElement("legend");
                    $(legend).text("مشخصات اضافی");
                    $("#machineModal").find("fieldset[name='udf']").append(legend);

                    var udfs = machine.udf;
                    for (var i= 0; i < udfs.length; i++){
                        var udf = udfs[i];
                        var row = createField();
                        $(row).find("input[name='fn']").val(udf.name);
                        $(row).find("select[name='ft']").val(udf.type);
                        $(row).find("input[name='fv']").val(udf.value);
                        $("#machineModal").find("fieldset[name='udf']").append(row);
                        $("#machineModal").find("fieldset[name='udf']").append("<hr/>");
                    }
                }
                addModal("machineModal");
                $("#machineModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#machineBody").append(tr);
        }
        createDataTable($("#machineTable"));
    }
    function productTable() {
        $("#productTable").DataTable().destroy();
        $("#productBody").children().remove();
        for (var i = 0; i < products.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", products[i]);
            $(a).on("click", function () {
                deleteRowProduct($(this).data("data"), "product")
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].size + "-" + products[i].design + "-" + products[i].pr);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).attr("cursor", "pointer");
            $(a).data("data", products[i]);
            $(a).on("click", function (event) {
                var product = $(this).data("data");
                $("#productModal").data("data", product);
                $("#productModal").find('input[name="pc"]').val(product.code);
                $("#productModal").find('input[name="ps"]').val(product.size);
                $("#productModal").find('input[name="pd"]').val(product.design);
                $("#productModal").find('input[name="pr"]').val(product.pr);
                $("#productModal").find('input[name="pw"]').val(product.weight);
                $("#productModal").find('textarea[name="des"]').val(product.des);
                if (product.udf != null){

                    $("#productModal").find("fieldset[name='udf']").children().remove();
                    var legend = document.createElement("legend");
                    $(legend).text("مشخصات اضافی");
                    $("#productModal").find("fieldset[name='udf']").append(legend);

                    var udfs = product.udf;
                    for (var i= 0; i < udfs.length; i++){
                        var udf = udfs[i];
                        var row = createField();
                        $(row).find("input[name='fn']").val(udf.name);
                        $(row).find("select[name='ft']").val(udf.type);
                        $(row).find("input[name='fv']").val(udf.value);
                        $("#productModal").find("fieldset[name='udf']").append(row);
                        $("#productModal").find("fieldset[name='udf']").append("<hr/>");
                    }
                }
                addModal("productModal");
                $("#productModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#productBody").append(tr);
        }
        createDataTable($("#productTable"));
    }
    function semiTable() {
        $("#productTable").DataTable().destroy();
        $("#productBody").children().remove();
        for (var i = 0; i < semistructureds.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", semistructureds[i]);
            $(a).on("click", function () {
                deleteRowProduct($(this).data("data"), "semi")
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semistructureds[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semistructureds[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).attr("cursor", "pointer");
            $(a).data("data", semistructureds[i]);
            $(a).on("click", function () {
                var semistructured = $(this).data("data");
                $("#semistructuredModal").data("data", semistructured);
                $("#semistructuredModal").find('input[name="ec"]').val(semistructured.code);
                $("#semistructuredModal").find('input[name="en"]').val(semistructured.name);
                $("#semistructuredModal").find('input[name="eu"]').val(semistructured.unit);
                $("#semistructuredModal").find('input[name="eamin"]').val(semistructured.ageMin);
                $("#semistructuredModal").find('input[name="eamax"]').val(semistructured.ageMax);
                $("#semistructuredModal").find('input[name="enmin"]').val(semistructured.needMin);
                $("#semistructuredModal").find('input[name="enmax"]').val(semistructured.needMax);
                $("#semistructuredModal").find('textarea[name="des"]').val(semistructured.des);
                if (semistructured.udf != null){

                    $("#semistructuredModal").find("fieldset[name='udf']").children().remove();
                    var legend = document.createElement("legend");
                    $(legend).text("مشخصات اضافی");
                    $("#semistructuredModal").find("fieldset[name='udf']").append(legend);

                    var udfs = semistructured.udf;
                    for (var i= 0; i < udfs.length; i++){
                        var udf = udfs[i];
                        var row = createField();
                        $(row).find("input[name='fn']").val(udf.name);
                        $(row).find("select[name='ft']").val(udf.type);
                        $(row).find("input[name='fv']").val(udf.value);
                        $("#semistructuredModal").find("fieldset[name='udf']").append(row);
                        $("#semistructuredModal").find("fieldset[name='udf']").append("<hr/>");
                    }
                }
                addModal("semistructuredModal");
                $("#semistructuredModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#productBody").append(tr);
        }
        createDataTable($("#productTable"));
    }

    function deleteRowGroup(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                groups.splice(groups.indexOf(data), 1);
                groupTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowMachine(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                machines.splice(machines.indexOf(data), 1);
                machineTable();
                alertify.message('حذف شد');
            }
            , function(){
            alertify.message('حذف نشد');
        });
    }
    function deleteRowProduct(data, kind) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                if (kind == "product"){
                    products.splice(products.indexOf(data), 1);
                    productTable();
                }else if (kind == "semi"){
                    semistructureds.splice(semistructureds.indexOf(data), 1);
                    semiTable();
                }
                checkProduct();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }

    function newModal(a) {
        var inputs = $("#" + a).find('input');
        for (var i = 0; i < inputs.length; i++){
            $(inputs[i]).val("");
        }
        var selects = $("#" + a).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
        }
        var textarea = $("#" + a).find('textarea');
        for (var i = 0; i < textarea.length; i++){
            $(textarea[i]).val("");
        }

        $("#" + a).find("fieldset[name='udf']").children().remove();
        var legend = document.createElement("legend");
        $(legend).text("مشخصات اضافی");
        $("#" + a).find("fieldset[name='udf']").append(legend);

        if (a == "groupModal"){
            machines = new Array();
            products = new Array();
            semistructureds = new Array();

            $("#machineBody").children().remove();
            $("#machineTable").DataTable().destroy();
            createDataTable($("#machineTable"));
            $("#productBody").children().remove();
            $("#productTable").DataTable().destroy();
            createDataTable($("#productTable"));
        }
        try{
            $("#" + a).data("data", null);
        }catch (err){

        }
        checkProduct();
        addModal(a);
    }

    function checkProduct() {
        if (products.length > 0){
            $("#semiButton").prop("disabled", true);
            $("#productButton").prop("disabled", false);
        }else if (semistructureds.length > 0){
            $("#productButton").prop("disabled", true);
            $("#semiButton").prop("disabled", false);
        }else {
            $("#productButton").prop("disabled", false);
            $("#semiButton").prop("disabled", false);
        }
    }

    function addFieldSectionRegister(element) {
        var row = createField();
        $(element).parents("div[class='modal-body']").first().find("fieldset[name='udf']").append(row);
        $(element).parents("div[class='modal-body']").first().find("fieldset[name='udf']").append("<hr/>");
    }
</script>
