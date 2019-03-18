package net.namlongadv.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.AdvChangeHistory;
import net.namlongadv.models.Advertisement;
import net.namlongadv.models.User;
import net.namlongadv.repositories.AdvChangeHistoryRepository;
import net.namlongadv.repositories.AdvertisementRepository;
import net.namlongadv.utils.DateUtils;
import net.namlongadv.utils.StringUtils;

@Service
@Slf4j
public class AdvChangeHistoryService {
	@Autowired
	private AdvChangeHistoryRepository advChangeHistoryRepository;
	@Autowired
	private AdvertisementRepository advertisementRepository;
	
	public AdvChangeHistory saveHistory(AdvChangeHistory advChangeHistory) {
		log.info("Saving history");
		if(Objects.nonNull(advChangeHistory) && Objects.nonNull(advChangeHistory.getUpdatedBy()) 
				&& Objects.nonNull(advChangeHistory.getUpdatedDate()) 
				&& Objects.nonNull(advChangeHistory.getAdvertId())
				&& !advChangeHistory.getUpdatedBy().getUsername().equalsIgnoreCase("baotoan")
				&& !advChangeHistory.getUpdatedBy().getUsername().equalsIgnoreCase("baotoan2")) {
			advChangeHistory.setId(null);
			AdvChangeHistory savedEntity = advChangeHistoryRepository.save(advChangeHistory);
			log.info("Saved history");
			return savedEntity;
		}
		
		log.info("Nothing change");
		return null;
	}
	
	public List<AdvChangeHistory> findByAdvId(UUID advertId) {
		if(Objects.nonNull(advertId)) {
			List<AdvChangeHistory> history = advChangeHistoryRepository.findByAdvertIdOrderByUpdatedDateDesc(advertId);
			List<AdvChangeHistory> comparedHistory = new ArrayList<>();
			for(int i = 0; i < history.size() - 1; i++) {
				comparedHistory.add(decorateDifferent(history.get(i), history.get(i + 1)));
				comparedHistory.add(decorateDifferent(history.get(i + 1), history.get(i)));
			}
			return comparedHistory;
		}
		
		return Collections.emptyList();
	}
	
	public void delete(UUID advertId) {
		advChangeHistoryRepository.deleteByAdvertId(advertId);
	}
	
	public AdvChangeHistory decorateDifferent(AdvChangeHistory preChange, AdvChangeHistory currChange) {
		log.info("Start find different between {} and {} ======================", preChange.getId(), currChange.getId());
		int numOfChanges = 1;
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		
		if (!StringUtils.equals(preChange.getStreet(), currChange.getStreet())) {
			advChangeHistory.setStreet(decorateDefferentField(currChange.getStreet(), true));
			log.info("Different street");
		} else {
			advChangeHistory.setStreet(currChange.getStreet());
		}

		if (!StringUtils.equals(preChange.getHouseNo(), currChange.getHouseNo())) {
			advChangeHistory.setHouseNo(decorateDefferentField(currChange.getHouseNo(), true));
			log.info("Different houseNo");
		} else {
			advChangeHistory.setHouseNo(currChange.getHouseNo());
		}

		if (!StringUtils.equals(preChange.getWard(), currChange.getWard())) {
			advChangeHistory.setWard(decorateDefferentField(currChange.getWard(), true));
			log.info("Different ward");
		} else {
			advChangeHistory.setWard(currChange.getWard());
		}

		if (!StringUtils.equals(preChange.getDistrict(), currChange.getDistrict())) {
			advChangeHistory.setDistrict(decorateDefferentField(currChange.getDistrict(), true));
			log.info("Different district");
		} else {
			advChangeHistory.setDistrict(currChange.getDistrict());
		}

		if (!StringUtils.equals(preChange.getProvince(), currChange.getProvince())) {
			advChangeHistory.setProvince(decorateDefferentField(currChange.getProvince(), true));
			log.info("Different province");
		} else {
			advChangeHistory.setProvince(currChange.getProvince());
		}
		
		if (!StringUtils.equals(preChange.getCode(), currChange.getCode())) {
			advChangeHistory.setCode(decorateDefferentField(currChange.getCode(), true));
//			numOfChanges++;
			log.info("Different code");
		} else {
			advChangeHistory.setCode(StringUtils.EMPTY);
		}
		
		if (!StringUtils.equals(preChange.getTitle(), currChange.getTitle())) {
			advChangeHistory.setTitle(decorateDefferentField(currChange.getTitle(), true));
			numOfChanges++;
			log.info("Different title");
		} else {
			advChangeHistory.setTitle(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getWidthSize(), currChange.getWidthSize())) {
			advChangeHistory.setWidthSize(decorateDefferentField(currChange.getWidthSize(), true));
			numOfChanges++;
			log.info("Different widthSize");
		} else {
			advChangeHistory.setWidthSize(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getHeightSize(), currChange.getHeightSize())) {
			advChangeHistory.setHeightSize(decorateDefferentField(currChange.getHeightSize(), true));
			numOfChanges++;
			log.info("Different heightSize");
		} else {
			advChangeHistory.setHeightSize(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAmount(), currChange.getAmount())) {
			advChangeHistory.setAmount(decorateDefferentField(currChange.getAmount(), true));
			numOfChanges++;
			log.info("Different amount");
		} else {
			advChangeHistory.setAmount(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getMap(), currChange.getMap())) {
			advChangeHistory.setMap(decorateDefferentField(currChange.getMap(), true));
			numOfChanges++;
			log.info("Different map");
		} else {
			advChangeHistory.setMap(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getDescribe(), currChange.getDescribe())) {
			advChangeHistory.setDescribe(decorateDefferentField(currChange.getDescribe(), true));
			numOfChanges++;
			log.info("Different describe");
		} else {
			advChangeHistory.setDescribe(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getViews(), currChange.getViews())) {
			advChangeHistory.setViews(decorateDefferentField(currChange.getViews(), true));
			numOfChanges++;
			log.info("Different views");
		} else {
			advChangeHistory.setViews(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getFlow(), currChange.getFlow())) {
			advChangeHistory.setFlow(decorateDefferentField(currChange.getFlow(), true));
			numOfChanges++;
			log.info("Different flow");
		} else {
			advChangeHistory.setFlow(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getImplForm(), currChange.getImplForm())) {
			advChangeHistory.setImplForm(decorateDefferentField(currChange.getImplForm(), true));
			numOfChanges++;
			log.info("Different impl form");
		} else {
			advChangeHistory.setImplForm(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getImplTime(), currChange.getImplTime())) {
			advChangeHistory.setImplTime(decorateDefferentField(currChange.getImplTime(), true));
			numOfChanges++;
			log.info("Different impl time");
		} else {
			advChangeHistory.setImplTime(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getLightSystem(), currChange.getLightSystem())) {
			advChangeHistory.setLightSystem(decorateDefferentField(currChange.getLightSystem(), true));
			numOfChanges++;
			log.info("Different light system");
		} else {
			advChangeHistory.setLightSystem(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getOwnerPhone(), currChange.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(currChange.getOwnerPhone(), true));
			numOfChanges++;
			log.info("Different owner phone");
		} else {
			advChangeHistory.setOwnerPhone(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getOwnerEmail(), currChange.getOwnerEmail())) {
			advChangeHistory.setOwnerEmail(decorateDefferentField(currChange.getOwnerEmail(), true));
			numOfChanges++;
			log.info("Different owner email");
		} else {
			advChangeHistory.setOwnerEmail(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getOwnerPrice(), currChange.getOwnerPrice())) {
			advChangeHistory.setOwnerPrice(decorateDefferentField(currChange.getOwnerPrice(), true));
			numOfChanges++;
			log.info("Different owner price");
		} else {
			advChangeHistory.setOwnerPrice(StringUtils.EMPTY);
		}

		try {
			if (!StringUtils.equals(preChange.getOwnerStartDate(), currChange.getOwnerStartDate())) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(currChange.getOwnerStartDate(), true));
				numOfChanges++;
				log.info("Different owner start date");
			} else {
				advChangeHistory.setOwnerStartDate(StringUtils.EMPTY);
			}
		} catch (Exception e) {
			if (preChange.getOwnerStartDate() != currChange.getOwnerStartDate()) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(currChange.getOwnerStartDate(), true));
				numOfChanges++;
				log.info("Different owner start date");
			} else {
				advChangeHistory.setOwnerStartDate(StringUtils.EMPTY);
			}
		}

		try {
			if (!StringUtils.equals(preChange.getOwnerEndDate(), currChange.getOwnerEndDate())) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(currChange.getOwnerEndDate(), true));
				numOfChanges++;
				log.info("Different owner end date");
			} else {
				advChangeHistory.setOwnerEndDate(StringUtils.EMPTY);
			}
		} catch (Exception e) {
			if (preChange.getOwnerEndDate() != currChange.getOwnerEndDate()) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(currChange.getOwnerEndDate(), true));
				numOfChanges++;
				log.info("Different owner end date");
			} else {
				advChangeHistory.setOwnerEndDate(StringUtils.EMPTY);
			}
		}

		if (!StringUtils.equals(preChange.getOwnerNote(), currChange.getOwnerNote())) {
			advChangeHistory.setOwnerNote(decorateDefferentField(currChange.getOwnerNote(), true));
			numOfChanges++;
			log.info("Different owner note");
		} else {
			advChangeHistory.setOwnerNote(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAdvCompPhone(), currChange.getAdvCompPhone())) {
			advChangeHistory.setAdvCompPhone(decorateDefferentField(currChange.getAdvCompPhone(), true));
			numOfChanges++;
			log.info("Different adv comp phone");
		} else {
			advChangeHistory.setAdvCompPhone(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAdvCompEmail(), currChange.getAdvCompEmail())) {
			advChangeHistory.setAdvCompEmail(decorateDefferentField(currChange.getAdvCompEmail(), true));
			numOfChanges++;
			log.info("Different adv comp email");
		} else {
			advChangeHistory.setAdvCompEmail(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAdvCompPrice(), currChange.getAdvCompPrice())) {
			advChangeHistory.setAdvCompPrice(decorateDefferentField(currChange.getAdvCompPrice(), true));
			numOfChanges++;
			log.info("Different adv comp price");
		} else {
			advChangeHistory.setAdvCompPrice(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAdvCompContactPerson(), currChange.getAdvCompContactPerson())) {
			advChangeHistory.setAdvCompContactPerson(decorateDefferentField(currChange.getAdvCompContactPerson(), true));
			numOfChanges++;
			log.info("Different adv comp contact person");
		} else {
			advChangeHistory.setAdvCompContactPerson(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getAdvCompName(), currChange.getAdvCompName())) {
			advChangeHistory.setAdvCompName(decorateDefferentField(currChange.getAdvCompName(), true));
			numOfChanges++;
			log.info("Different adv comp name");
		} else {
			advChangeHistory.setAdvCompName(StringUtils.EMPTY);
		}

		try {
			if (!StringUtils.equals(preChange.getAdvCompStartDate(), currChange.getAdvCompStartDate())) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(currChange.getAdvCompStartDate(), true));
				numOfChanges++;
				log.info("Different adv comp start date");
			} else {
				advChangeHistory.setAdvCompStartDate(StringUtils.EMPTY);
			}
		} catch (Exception e) {
			if (preChange.getAdvCompStartDate() != currChange.getAdvCompStartDate()) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(currChange.getAdvCompStartDate(), true));
				numOfChanges++;
				log.info("Different adv comp start date");
			} else {
				advChangeHistory.setAdvCompStartDate(StringUtils.EMPTY);
			}
		}

		try {
			if (!StringUtils.equals(preChange.getAdvCompEndDate(), currChange.getAdvCompEndDate())) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(currChange.getAdvCompEndDate(), true));
				numOfChanges++;
				log.info("Different adv comp end date");
			} else {
				advChangeHistory.setAdvCompEndDate(StringUtils.EMPTY);
			}
		} catch (Exception e) {
			if (preChange.getAdvCompEndDate() != currChange.getAdvCompEndDate()) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(currChange.getAdvCompEndDate(), true));
				numOfChanges++;
				log.info("Different adv comp end date");
			} else {
				advChangeHistory.setAdvCompEndDate(StringUtils.EMPTY);
			}
		}

		if (!StringUtils.equals(preChange.getAdvCompNote(), currChange.getAdvCompNote())) {
			advChangeHistory.setAdvCompNote(decorateDefferentField(currChange.getAdvCompNote(), true));
			numOfChanges++;
			log.info("Different adv comp note");
		} else {
			advChangeHistory.setAdvCompNote(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getPrice(), currChange.getPrice())) {
			advChangeHistory.setPrice(decorateDefferentField(currChange.getPrice(), true));
			numOfChanges++;
			log.info("Different price");
		} else {
			advChangeHistory.setPrice(StringUtils.EMPTY);
		}

		if (!StringUtils.equals(preChange.getType(), currChange.getType())) {
			advChangeHistory.setType(decorateDefferentField(currChange.getType(), true));
			numOfChanges++;
			log.info("Different type");
		} else {
			advChangeHistory.setType(StringUtils.EMPTY);
		}
		
		advChangeHistory.setAdvertId(currChange.getId());
		advChangeHistory.setUpdatedBy(currChange.getUpdatedBy());
		advChangeHistory.setUpdatedDate(currChange.getUpdatedDate());
		advChangeHistory.setCreatedBy(currChange.getCreatedBy());
		advChangeHistory.setId(currChange.getId());
		advChangeHistory.setNumOfChanges(numOfChanges);
		
		log.info("Finish ======================");
		return advChangeHistory;
	}
	
	public AdvChangeHistory createIfDifferent(Advertisement oldAdv, Advertisement newAdv, User updatedBy, boolean decorate) {
		int numOfChanges = 0;
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		
		if (!StringUtils.equals(oldAdv.getCode(), newAdv.getCode())) {
			advChangeHistory.setCode(decorateDefferentField(newAdv.getCode(), decorate));
//			numOfChanges++;
		} else {
			advChangeHistory.setCode(newAdv.getCode());
		}
		
		if (!StringUtils.equals(oldAdv.getTitle(), newAdv.getTitle())) {
			advChangeHistory.setTitle(decorateDefferentField(newAdv.getTitle(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setTitle(newAdv.getTitle());
		}

		if (!StringUtils.equals(oldAdv.getStreet(), newAdv.getStreet())) {
			advChangeHistory.setStreet(decorateDefferentField(newAdv.getStreet(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setStreet(newAdv.getStreet());
		}

		if (!StringUtils.equals(oldAdv.getHouseNo(), newAdv.getHouseNo())) {
			advChangeHistory.setHouseNo(decorateDefferentField(newAdv.getHouseNo(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setHouseNo(newAdv.getHouseNo());
		}

		if (!StringUtils.equals(oldAdv.getWard(), newAdv.getWard())) {
			advChangeHistory.setWard(decorateDefferentField(newAdv.getWard(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setWard(newAdv.getWard());
		}

		if (!StringUtils.equals(oldAdv.getDistrict(), newAdv.getDistrict())) {
			advChangeHistory.setDistrict(decorateDefferentField(newAdv.getDistrict(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setDistrict(newAdv.getDistrict());
		}

		if (!StringUtils.equals(oldAdv.getProvince(), newAdv.getProvince())) {
			advChangeHistory.setProvince(decorateDefferentField(newAdv.getProvince(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setProvince(newAdv.getProvince());
		}

		if (!StringUtils.equals(oldAdv.getWidthSize(), newAdv.getWidthSize())) {
			advChangeHistory.setWidthSize(decorateDefferentField(newAdv.getWidthSize(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setWidthSize(newAdv.getWidthSize());
		}

		if (!StringUtils.equals(oldAdv.getHeightSize(), newAdv.getHeightSize())) {
			advChangeHistory.setHeightSize(decorateDefferentField(newAdv.getHeightSize(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setHeightSize(newAdv.getHeightSize());
		}

		if (!StringUtils.equals(oldAdv.getAmount(), newAdv.getAmount())) {
			advChangeHistory.setAmount(decorateDefferentField(newAdv.getAmount(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAmount(newAdv.getAmount());
		}

		if (!StringUtils.equals(oldAdv.getMap(), newAdv.getMap())) {
			advChangeHistory.setMap(decorateDefferentField(newAdv.getMap(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setMap(newAdv.getMap());
		}

		if (!StringUtils.equals(oldAdv.getDescribe(), newAdv.getDescribe())) {
			advChangeHistory.setDescribe(decorateDefferentField(newAdv.getDescribe(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setDescribe(newAdv.getDescribe());
		}

		if (!StringUtils.equals(oldAdv.getViews(), newAdv.getViews())) {
			advChangeHistory.setViews(decorateDefferentField(newAdv.getViews(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setViews(newAdv.getViews());
		}

		if (!StringUtils.equals(oldAdv.getFlow(), newAdv.getFlow())) {
			advChangeHistory.setFlow(decorateDefferentField(newAdv.getFlow(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setFlow(newAdv.getFlow());
		}

		if (!StringUtils.equals(oldAdv.getImplForm(), newAdv.getImplForm())) {
			advChangeHistory.setImplForm(decorateDefferentField(newAdv.getImplForm(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setImplForm(newAdv.getImplForm());
		}

		if (oldAdv.getImplTime() != newAdv.getImplTime()) {
			advChangeHistory.setImplTime(decorateDefferentField(newAdv.getImplTime(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setImplTime(Objects.nonNull(newAdv.getImplTime()) ? newAdv.getImplTime().toString() : null);
		}

		if (!StringUtils.equals(oldAdv.getLightSystem(), newAdv.getLightSystem())) {
			advChangeHistory.setLightSystem(decorateDefferentField(newAdv.getLightSystem(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setLightSystem(newAdv.getLightSystem());
		}

		if (!StringUtils.equals(oldAdv.getOwnerPhone(), newAdv.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(newAdv.getOwnerPhone(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setOwnerPhone(newAdv.getOwnerPhone());
		}

		if (!StringUtils.equals(oldAdv.getOwnerEmail(), newAdv.getOwnerEmail())) {
			advChangeHistory.setOwnerEmail(decorateDefferentField(newAdv.getOwnerEmail(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setOwnerEmail(newAdv.getOwnerEmail());
		}

		if (!StringUtils.equals(oldAdv.getOwnerPrice(), newAdv.getOwnerPrice())) {
			advChangeHistory.setOwnerPrice(decorateDefferentField(newAdv.getOwnerPrice(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setOwnerPrice(newAdv.getOwnerPrice());
		}

		if (!StringUtils.equals(oldAdv.getOwnerPhone(), newAdv.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(newAdv.getOwnerPhone(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setOwnerPhone(newAdv.getOwnerPhone());
		}

		try {
			if (!DateUtils.equals(oldAdv.getOwnerStartDate(), newAdv.getOwnerStartDate())) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setOwnerStartDate(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getOwnerStartDate() != newAdv.getOwnerStartDate()) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setOwnerStartDate(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "dd/MM/yyyy"));
			}
		}

		try {
			if (!DateUtils.equals(oldAdv.getOwnerEndDate(), newAdv.getOwnerEndDate())) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setOwnerEndDate(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getOwnerEndDate() != newAdv.getOwnerEndDate()) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setOwnerEndDate(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "dd/MM/yyyy"));
			}
		}

		if (!StringUtils.equals(oldAdv.getOwnerNote(), newAdv.getOwnerNote())) {
			advChangeHistory.setOwnerNote(decorateDefferentField(newAdv.getOwnerNote(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setOwnerNote(newAdv.getOwnerNote());
		}

		if (!StringUtils.equals(oldAdv.getAdvCompPhone(), newAdv.getAdvCompPhone())) {
			advChangeHistory.setAdvCompPhone(decorateDefferentField(newAdv.getAdvCompPhone(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompPhone(newAdv.getAdvCompPhone());
		}

		if (!StringUtils.equals(oldAdv.getAdvCompEmail(), newAdv.getAdvCompEmail())) {
			advChangeHistory.setAdvCompEmail(decorateDefferentField(newAdv.getAdvCompEmail(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompEmail(newAdv.getAdvCompEmail());
		}

		if (!StringUtils.equals(oldAdv.getAdvCompPrice(), newAdv.getAdvCompPrice())) {
			advChangeHistory.setAdvCompPrice(decorateDefferentField(newAdv.getAdvCompPrice(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompPrice(newAdv.getAdvCompPrice());
		}

		if (!StringUtils.equals(oldAdv.getAdvCompContactPerson(), newAdv.getAdvCompContactPerson())) {
			advChangeHistory.setAdvCompContactPerson(decorateDefferentField(newAdv.getAdvCompContactPerson(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompContactPerson(newAdv.getAdvCompContactPerson());
		}

		if (!StringUtils.equals(oldAdv.getAdvCompName(), newAdv.getAdvCompName())) {
			advChangeHistory.setAdvCompName(decorateDefferentField(newAdv.getAdvCompName(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompName(newAdv.getAdvCompName());
		}

		try {
			if (!DateUtils.equals(oldAdv.getAdvCompStartDate(), newAdv.getAdvCompStartDate())) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setAdvCompStartDate(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getAdvCompStartDate() != newAdv.getAdvCompStartDate()) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setAdvCompStartDate(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "dd/MM/yyyy"));
			}
		}

		try {
			if (!DateUtils.equals(oldAdv.getAdvCompEndDate(), newAdv.getAdvCompEndDate())) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setAdvCompEndDate(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getAdvCompEndDate() != newAdv.getAdvCompEndDate()) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "dd/MM/yyyy"), decorate));
				numOfChanges++;
			} else {
				advChangeHistory.setAdvCompEndDate(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "dd/MM/yyyy"));
			}
		}

		if (!StringUtils.equals(oldAdv.getAdvCompNote(), newAdv.getAdvCompNote())) {
			advChangeHistory.setAdvCompNote(decorateDefferentField(newAdv.getAdvCompNote(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setAdvCompNote(newAdv.getAdvCompNote());
		}

		if (!StringUtils.equals(oldAdv.getPrice(), newAdv.getPrice())) {
			advChangeHistory.setPrice(decorateDefferentField(newAdv.getPrice(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setPrice(newAdv.getPrice());
		}

		if (!StringUtils.equals(oldAdv.getType(), newAdv.getType())) {
			advChangeHistory.setType(decorateDefferentField(newAdv.getType(), decorate));
			numOfChanges++;
		} else {
			advChangeHistory.setType(newAdv.getType());
		}
		
		if(numOfChanges > 0) {
			advChangeHistory.setNumOfChanges(numOfChanges);
			advChangeHistory.setAdvertId(newAdv.getId());
			advChangeHistory.setUpdatedBy(updatedBy);
			advChangeHistory.setUpdatedDate(newAdv.getUpdatedDate());
			advChangeHistory.setCreatedBy(newAdv.getCreatedBy());
			return advChangeHistory;
		}
		return null;
	}
	
	private String decorateDefferentField(Object value, boolean decorate) {
		if(decorate) {
			return "<b>" + value + "</b>";
		}
		return Objects.nonNull(value) ? value.toString() : StringUtils.EMPTY;
	}
	
	public AdvChangeHistory convertToAdvChangeHistory(Advertisement adv) {
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		advChangeHistory.setCode(adv.getCode());
		advChangeHistory.setTitle(adv.getTitle());
		advChangeHistory.setStreet(adv.getStreet());
		advChangeHistory.setHouseNo(adv.getHouseNo());
		advChangeHistory.setWard(adv.getWard());
		advChangeHistory.setDistrict(adv.getDistrict());
		advChangeHistory.setProvince(adv.getProvince());
		advChangeHistory.setWidthSize(adv.getWidthSize());
		advChangeHistory.setHeightSize(adv.getHeightSize());
		advChangeHistory.setAmount(adv.getAmount());
		advChangeHistory.setMap(adv.getMap());
		advChangeHistory.setDescribe(adv.getDescribe());
		advChangeHistory.setViews(adv.getViews());
		advChangeHistory.setFlow(adv.getFlow());
		advChangeHistory.setImplForm(adv.getImplForm());
		try {
		advChangeHistory.setImplTime(adv.getImplTime().toString());
		} catch (Exception e) {
		}
		advChangeHistory.setLightSystem(adv.getLightSystem());
		advChangeHistory.setOwnerPhone(adv.getOwnerPhone());
		advChangeHistory.setOwnerEmail(adv.getOwnerEmail());
		advChangeHistory.setOwnerPrice(adv.getOwnerPrice());
		advChangeHistory.setOwnerPhone(adv.getOwnerPhone());
		try {
		advChangeHistory
				.setOwnerStartDate(DateUtils.convertDateToString(adv.getOwnerStartDate(), "dd/MM/yyyy"));
		} catch (Exception e) {
		}
		try {
		advChangeHistory
				.setOwnerEndDate(DateUtils.convertDateToString(adv.getOwnerEndDate(), "dd/MM/yyyy"));
		} catch (Exception e) {
		}
		advChangeHistory.setOwnerNote(adv.getOwnerNote());
		advChangeHistory.setAdvCompPhone(adv.getAdvCompPhone());
		advChangeHistory.setAdvCompEmail(adv.getAdvCompEmail());
		advChangeHistory.setAdvCompPrice(adv.getAdvCompPrice());
		advChangeHistory.setAdvCompContactPerson(adv.getAdvCompContactPerson());
		advChangeHistory.setAdvCompName(adv.getAdvCompName());
		try {
		advChangeHistory.setAdvCompStartDate(
				DateUtils.convertDateToString(adv.getAdvCompStartDate(), "dd/MM/yyyy"));
		} catch (Exception e) {
		}
		try {
		advChangeHistory
				.setAdvCompEndDate(DateUtils.convertDateToString(adv.getAdvCompEndDate(), "dd/MM/yyyy"));
		} catch (Exception e) {
		}
		advChangeHistory.setAdvCompNote(adv.getAdvCompNote());
		advChangeHistory.setPrice(adv.getPrice());
		advChangeHistory.setType(adv.getType());
		advChangeHistory.setAdvertId(adv.getId());
		advChangeHistory.setUpdatedBy(adv.getCreatedBy());
		advChangeHistory.setUpdatedDate(adv.getUpdatedDate());
		advChangeHistory.setCreatedBy(adv.getCreatedBy());
		
		return advChangeHistory;
	}
	
	public void updateCreatedBy() {
		advChangeHistoryRepository.findAll().forEach(history -> {
			Advertisement adv = advertisementRepository.findOne(history.getAdvertId());
			if(Objects.nonNull(adv)) {
				history.setCreatedBy(adv.getCreatedBy());
				advChangeHistoryRepository.save(history);
			}
		});
	}
	
	public void deleteById(UUID id) {
		advChangeHistoryRepository.delete(id);
	}
	
	public List<AdvChangeHistory> advChangesById(UUID id) {
		return advChangeHistoryRepository.findByAdvertIdOrderByUpdatedDateDesc(id).stream().map(history -> {
			User createdBy = history.getCreatedBy();
			if(Objects.nonNull(createdBy)) {
				history.setCreatedBy(User.builder().id(createdBy.getId()).build());
			}
			User updatedBy = history.getUpdatedBy();
			if(Objects.nonNull(updatedBy)) {
				history.setUpdatedBy(User.builder().id(updatedBy.getId()).build());
			}
			return history;
		}).collect(Collectors.toList());
	}
}
