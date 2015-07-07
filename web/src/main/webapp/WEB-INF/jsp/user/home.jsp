<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

	<div role="main" class="ui-content jqm-content">
			<h2>Non-inset, collapsible set</h2>
			<p>Setting <code>data-inset="false"</code> on a collapsible or a collapsible set makes the collapsible full width (non-inset), like a full width listview.</p>
			
			<div data-role="collapsible-set" data-theme="b" data-content-theme="d" data-inset="false">
				<div data-role="collapsible">
					<h2>消息</h2>
					<ul data-role="listview">
						<li><a href="index.html">Inbox <span class="ui-li-count">12</span></a></li>
						<li><a href="index.html">Outbox <span class="ui-li-count">0</span></a></li>
						<li><a href="index.html">Drafts <span class="ui-li-count">4</span></a></li>
						<li><a href="index.html">Sent <span class="ui-li-count">328</span></a></li>
						<li><a href="index.html">Trash <span class="ui-li-count">62</span></a></li>
					</ul>
				</div>
				<div data-role="collapsible">
					<h2>面试日历</h2>
					<ul data-role="listview" data-theme="d" data-divider-theme="d">
						<li data-role="list-divider">Friday, October 8, 2010 <span class="ui-li-count">2</span></li>
						<li><a href="index.html">
								<h3>Stephen Weber</h3>
								<p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
								<p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
								<p class="ui-li-aside"><strong>6:24</strong>PM</p>
						</a></li>
						<li><a href="index.html">
							<h3>jQuery Team</h3>
							<p><strong>Boston Conference Planning</strong></p>
							<p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
							<p class="ui-li-aside"><strong>9:18</strong>AM</p>
						</a></li>
						<li data-role="list-divider">Thursday, October 7, 2010 <span class="ui-li-count">1</span></li>
						<li><a href="index.html">
							<h3>Avery Walker</h3>
							<p><strong>Re: Dinner Tonight</strong></p>
							<p>Sure, let's plan on meeting at Highland Kitchen at 8:00 tonight. Can't wait! </p>
							<p class="ui-li-aside"><strong>4:48</strong>PM</p>
						</a></li>
						<li data-role="list-divider">Wednesday, October 6, 2010 <span class="ui-li-count">3</span></li>
						<li><a href="index.html">
							<h3>Amazon.com</h3>
							<p><strong>4-for-3 Books for Kids</strong></p>
							<p>As someone who has purchased children's books from our 4-for-3 Store, you may be interested in these featured books.</p>
							<p class="ui-li-aside"><strong>12:47</strong>PM</p>
						</a></li>
					</ul>
				</div>
				<div data-role="collapsible">
					<h2>Contacts</h2>
					<ul data-role="listview" data-autodividers="true" data-theme="d" data-divider-theme="d">
						<li><a href="index.html">Adam Kinkaid</a></li>
						<li><a href="index.html">Alex Wickerham</a></li>
						<li><a href="index.html">Avery Johnson</a></li>
						<li><a href="index.html">Bob Cabot</a></li>
						<li><a href="index.html">Caleb Booth</a></li>
						<li><a href="index.html">Christopher Adams</a></li>
						<li><a href="index.html">Culver James</a></li>	
					</ul>
				</div>
			</div>
        </div>