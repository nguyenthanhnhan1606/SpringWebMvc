/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntn.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ntn.dto.DonHangDto;
import com.ntn.pojo.Donhang;
import com.ntn.repository.DonHangRepository;
import com.ntn.service.DonHangService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author THANH NHAN
 */
@Service
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangRepository DonHangRepo;
    @Autowired
    private Cloudinary cloudinary;

    private DonHangDto toDto(Donhang dh) {
        DonHangDto dh1 = new DonHangDto();
        dh1.setId(dh.getId());
        dh1.setNoigui(dh.getNoigui());
        dh1.setNoinhan(dh.getNoinhan());
        dh1.setMota(dh.getMota());
        dh1.setNgaytao(dh.getNgaytao());
        dh1.setGiatridh(dh.getGiatridh());
        dh1.setTrangthai(dh.getTrangthai());
        dh1.setGhichu(dh.getGhichu());
        dh1.setIdUser(dh.getIdUser().getId());
        dh1.setIdShipper(dh.getIdShipper().getId());
        dh1.setIdKhuyenmai(dh.getIdKhuyenmai().getId());
        dh1.setImage(dh.getImage());
        return dh1;
    }

    @Override
    public boolean addOrUpdateDh(Donhang dh) {
        if (dh.getFile() != null && !dh.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(dh.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                dh.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(DonHangServiceImpl.class.getName()).log(Level.SEVERE, "Lỗi khi tải lên file: " + ex.getMessage(), ex);
                throw new RuntimeException("Đã xảy ra lỗi khi tải lên file. Vui lòng thử lại hoặc liên hệ hỗ trợ.");
            }
        }

        return this.DonHangRepo.addOrUpdateDh(dh);
    }

    @Override
    public List<Donhang> getAlls(int id, Map<String, String> params) {
        return this.DonHangRepo.getAlls(id, params);
    }

    @Override
    public List<Donhang> getOrderByStatus(Map<String, String> params) {
        return this.DonHangRepo.getOrderByStatus(params);
    }

    @Override
    public Long countOrder() {
        return this.DonHangRepo.countOrder();
    }

    @Override
    public Donhang getOrderById(int id) {
        return this.DonHangRepo.getOrderById(id);
    }

    @Override
    public List<Donhang> getDonHangsByShipperId(int id, Map<String, String> params) {
        return this.DonHangRepo.getDonHangsByShipperId(id, params);
    }

    @Override
    public List<Donhang> getDonHangSuccessByShipperId(int id, Map<String, String> params) {
        return this.DonHangRepo.getDonHangSuccessByShipperId(id, params);
    }

    @Override
    public List<Donhang> getAllOrder(Map<String, String> params) {
        return this.DonHangRepo.getAllOrder(params);
    }

    @Override
    public Long countOrderNew() {
        return this.DonHangRepo.countOrderNew();
    }

    @Override
    public Long countOrderDG() {
        return this.DonHangRepo.countOrderDG();
    }

    @Override
    public List<Donhang> getOrderByStatusNew(Map<String, String> params) {
        return this.DonHangRepo.getOrderByStatusNew(params);
    }

    @Override
    public List<Donhang> getOrderByStatusDG(Map<String, String> params) {
        return this.DonHangRepo.getOrderByStatusDG(params);
    }

}
